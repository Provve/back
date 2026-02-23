package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.domain.entity.ExamAddVote;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.domain.value.VoteReactions;
import tech.provve.skill.mapper.VoteMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static tech.provve.skill.db.generated.tables.ExamAddVote.EXAM_ADD_VOTE;
import static tech.provve.skill.db.generated.tables.GetReactionsTotal.GET_REACTIONS_TOTAL;
import static tech.provve.skill.db.generated.tables.Reactions.REACTIONS;
import static tech.provve.skill.db.generated.tables.Vote.VOTE;
import static tech.provve.skill.domain.entity.Vote.Type.ADD_EXAM;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class VoteRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Vote> outputMapper = record -> {
        ExamAddVote examAddVote = record.map(_ -> new ExamAddVote(
                record.get(EXAM_ADD_VOTE.SKILL_NAME),
                record.get(EXAM_ADD_VOTE.DESCRIPTION),
                record.get(EXAM_ADD_VOTE.MATERIAL_URL)
        ));
        VoteReactions reactions = record.map(_ -> new VoteReactions(
                record.get(GET_REACTIONS_TOTAL.TOTAL_POSITIVE),
                record.get(GET_REACTIONS_TOTAL.TOTAL_NEGATIVE)
        ));

        return new Vote(
                record.get(VOTE.NAME),
                record.get(VOTE.ACTIVE),
                record.get(VOTE.SUCCESS),
                record.get(VOTE.AUTHOR),
                record.get(VOTE.DEADLINE),
                record.get(VOTE.ARGUMENTS),
                record.get(
                        VOTE.TYPE, Converter.from(
                                Short.class, Vote.Type.class,
                                code -> Vote.Type.map(code)
                        )
                ),
                List.of(record.get(VOTE.TAGS)),
                examAddVote,
                reactions
        );
    };

    public void save(Vote vote) {
        dsl.insertInto(VOTE)
           .set(VoteMapper.INSTANCE.map(vote))
           .execute();

        if (ADD_EXAM.equals(vote.type())) {
            dsl.insertInto(EXAM_ADD_VOTE)
               .set(VoteMapper.INSTANCE.map(
                       Objects.requireNonNull(vote.examAddVote()),
                       vote.name()
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

    @SuppressWarnings("all")
    public List<Vote> getAll() {
        var select = dsl.select()
                        .from(VOTE)
                        .leftJoin(EXAM_ADD_VOTE)
                        .on(EXAM_ADD_VOTE.VOTE_NAME.eq(VOTE.NAME))
                        .crossJoin(GET_REACTIONS_TOTAL.call(VOTE.NAME));
        return dsl.fetchStream(select)
                  .map(result -> result.map(outputMapper))
                  .toList();
    }

    public void setReaction(String name, String voter, boolean reaction) {
        dsl.insertInto(REACTIONS)
           .set(REACTIONS.VOTE_NAME, name)
           .set(REACTIONS.VOTER, voter)
           .set(REACTIONS.REACTION, reaction)
           .execute();
    }

    /**
     * Automatically sets <code>active = false</code> by business rule
     */
    public void updateSuccess(String name, boolean success) {
        dsl.update(VOTE)
           .set(VOTE.ACTIVE, false)
           .set(VOTE.SUCCESS, success)
           .where(VOTE.NAME.eq(name))
           .execute();
    }

}
