package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.domain.entity.Comment;
import tech.provve.skill.mapper.comment.CommentJooqMapper;

import java.util.List;
import java.util.Optional;

import static tech.provve.skill.db.generated.tables.Comment.COMMENT;

@NullMarked
@Singleton
@RequiredArgsConstructor
public class CommentRepository {

    @External
    private final DSLContext dsl;

    private final RecordMapper<Record, Comment> outputMapper = record -> new Comment(
            record.get(COMMENT.ID),
            record.get(COMMENT.AUTHOR),
            record.get(COMMENT.CONTENT),
            record.get(COMMENT.CREATED)
                  .toLocalDateTime(),
            record.get(COMMENT.VOTE_NAME),
            record.get(COMMENT.REPLY_FOR)
    );

    public void save(Comment comment) {
        dsl.insertInto(COMMENT)
           .set(CommentJooqMapper.INSTANCE.map(comment))
           .execute();
    }

    public void update(Integer id, String content) {
        dsl.update(COMMENT)
           .set(COMMENT.CONTENT, content)
           .where(COMMENT.ID.eq(id))
           .execute();
    }

    public void delete(Integer id) {
        dsl.deleteFrom(COMMENT)
           .where(COMMENT.ID.eq(id))
           .execute();
    }

    public Optional<Comment> get(Integer id) {
        return dsl.select()
                  .from(COMMENT)
                  .where(COMMENT.ID.eq(id))
                  .fetchOptional()
                  .map(outputMapper);
    }

    @SuppressWarnings("all")
    public List<Comment> getAll(String previous, int pageSize) {
        var select = dsl.select()
                        .from(COMMENT)
                        .orderBy(COMMENT.CREATED)
                        .limit(pageSize);
        return dsl.fetchMany(select)
                  .stream()
                  .map(result -> result.map(outputMapper))
                  .findAny()
                  .get();
    }
}
