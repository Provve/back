package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.api.server.generated.dto.Condition;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.skill.domain.entity.Exam;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.domain.value.VoteReactions;
import tech.provve.skill.mapper.VoteMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static tech.provve.skill.db.generated.tables.ExamAddVote.EXAM_ADD_VOTE;
import static tech.provve.skill.db.generated.tables.GetReactionsTotal.GET_REACTIONS_TOTAL;
import static tech.provve.skill.db.generated.tables.Reactions.REACTIONS;
import static tech.provve.skill.db.generated.tables.Vote.VOTE;
import static tech.provve.skill.domain.entity.Vote.Type.ADD_EXAM;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class VoteRepository extends Filtering {

    @External
    private final DSLContext dsl;

    private static final Map<String, Function<tech.provve.api.server.generated.dto.Condition, org.jooq.Condition>> FIELD_CONDITION_MAPPERS = Map.of(
            "name", condition -> switch (condition.getOperator()) {
                case EQ -> VOTE.NAME.eq(condition.getValue());
                case LIKE -> VOTE.NAME.like(condition.getValue());
            },
            "active", condition -> switch (condition.getOperator()) {
                case EQ -> VOTE.ACTIVE.eq(Boolean.parseBoolean(condition.getValue()));
                case LIKE -> VOTE.ACTIVE.like(condition.getValue());
            },
            "author", condition -> switch (condition.getOperator()) {
                case EQ -> VOTE.AUTHOR.eq(condition.getValue());
                case LIKE -> VOTE.AUTHOR.like(condition.getValue());
            },
            "arguments", condition -> switch (condition.getOperator()) {
                case EQ -> VOTE.ARGUMENTS.eq(condition.getValue());
                case LIKE -> VOTE.ARGUMENTS.like(condition.getValue());
            },
            "tags", condition -> switch (condition.getOperator()) {
                case EQ -> VOTE.TAGS.in(condition.getValue()
                                                 .substring(1,
                                                            condition.getValue()
                                                                     .length() - 1) // remove [ ]
                                                 .split(",\\s?"));
                case LIKE -> VOTE.TAGS.like(condition.getValue());
            }
    );

    private final RecordMapper<Record, Vote> outputMapper = record -> {
        Exam exam = record.map(_ -> new Exam(
                record.get(VOTE.NAME),
                record.get(EXAM_ADD_VOTE.SKILL_NAME),
                record.get(EXAM_ADD_VOTE.DESCRIPTION),
                record.get(EXAM_ADD_VOTE.PUBLIC_ARCHIVE_URL),
                record.get(EXAM_ADD_VOTE.PRIVATE_ARCHIVE_URL)
        ));
        VoteReactions reactions = record.map(_ -> new VoteReactions(
                record.get(GET_REACTIONS_TOTAL.TOTAL_POSITIVE),
                record.get(GET_REACTIONS_TOTAL.TOTAL_NEGATIVE)
        ));

        return Vote.builder()
                   .name(record.get(VOTE.NAME))
                   .active(record.get(VOTE.ACTIVE))
                   .success(record.get(VOTE.SUCCESS))
                   .author(record.get(VOTE.AUTHOR))
                   .deadline(record.get(VOTE.DEADLINE))
                   .arguments(record.get(VOTE.ARGUMENTS))
                   .type(record.get(
                           VOTE.TYPE,
                           Converter.from(
                                   Short.class,
                                   Vote.Type.class,
                                   code -> Vote.Type.map(code)
                           )
                   ))
                   .tags(List.of(record.get(VOTE.TAGS)))
                   .exam(exam)
                   .reactions(reactions)
                   .build();
    };

    public void save(Vote vote) {
        dsl.insertInto(VOTE)
           .set(VoteMapper.INSTANCE.map(vote))
           .execute();

        if (ADD_EXAM.equals(vote.getType())) {
            dsl.insertInto(EXAM_ADD_VOTE)
               .set(VoteMapper.INSTANCE.map(
                       Objects.requireNonNull(vote.getExam()),
                       vote.getName()
               ))
               .execute();
        }
    }

    public Optional<Vote> findByName(String name) {
        return dsl.select()
                  .from(VOTE)
                  .leftJoin(EXAM_ADD_VOTE)
                  .on(EXAM_ADD_VOTE.VOTE_NAME.eq(name))
                  .crossJoin(GET_REACTIONS_TOTAL.call(VOTE.NAME))
                  .where(VOTE.NAME.eq(name))
                  .fetchOptional(outputMapper);
    }

    public boolean exists(String name) {
        return nonNull(dsl.select(VOTE.NAME)
                          .from(VOTE)
                          .where(VOTE.NAME.eq(name))
                          .fetchOne());
    }

    @SuppressWarnings("all")
    public List<Vote> getAll(Filter filter, String previous, int pageSize) {
        var select = dsl.select()
                        .from(VOTE)
                        .leftJoin(EXAM_ADD_VOTE)
                        .on(EXAM_ADD_VOTE.VOTE_NAME.eq(VOTE.NAME))
                        .crossJoin(GET_REACTIONS_TOTAL.call(VOTE.NAME))
                        .where(jooqConditions(filter.getConditions()))
                        .orderBy(VOTE.NAME)
                        .seek(previous)
                        .limit(pageSize);
        return dsl.fetchStream(select)
                  .map(result -> result.map(outputMapper))
                  .toList();
    }

    /**
     * @param voter    who is voting
     * @param reaction +/-
     */
    public void setReaction(String name, String voter, boolean reaction) {
        dsl.insertInto(REACTIONS)
           .set(REACTIONS.VOTE_NAME,
                name)
           .set(REACTIONS.VOTER,
                voter)
           .set(REACTIONS.REACTION,
                reaction)
           .execute();
    }

    /**
     * Automatically sets <code>active = false</code> by business rule
     */
    public void updateSuccess(String name, boolean success) {
        dsl.update(VOTE)
           .set(VOTE.ACTIVE,
                false)
           .set(VOTE.SUCCESS,
                success)
           .where(VOTE.NAME.eq(name))
           .execute();
    }

    @Override
    protected Map<String, Function<Condition, org.jooq.Condition>> fieldConditionMappers() {
        return FIELD_CONDITION_MAPPERS;
    }
}
