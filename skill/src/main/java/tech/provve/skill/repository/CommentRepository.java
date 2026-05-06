package tech.provve.skill.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jspecify.annotations.NullMarked;
import tech.provve.skill.domain.entity.Comment;

import java.util.*;

import static tech.provve.skill.db.generated.tables.Comment.COMMENT;
import static tech.provve.skill.db.generated.tables.GetCommentsTree.GET_COMMENTS_TREE;

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
            record.get(COMMENT.PARENT_ID)
    );

    public void save(Comment comment) {
        dsl.insertInto(COMMENT)
           .set(COMMENT.AUTHOR, comment.author())
           .set(COMMENT.CONTENT, comment.content())
           .set(COMMENT.VOTE_NAME, comment.voteName())
           .set(COMMENT.PARENT_ID, comment.parentId())
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

    /**
     * @return key — anchror <br>
     * value — list of replies
     */
    @SuppressWarnings("all")
    public Map<Comment, List<Comment>> getAll(String previous, int pageSize, String voteName) {
        var select = dsl.select(GET_COMMENTS_TREE.call(Integer.parseInt(previous), voteName, pageSize));
        List<Comment> anchorsAndReplies = dsl.fetchMany(select)
                                             .stream()
                                             .map(result -> result.map(outputMapper))
                                             .findAny()
                                             .get();

        List<Comment> anchors = anchorsAndReplies.stream()
                                                 .filter(c -> null == c.parentId())
                                                 .toList();
        List<Comment> replies = anchorsAndReplies.stream()
                                                 .filter(c -> null != c.parentId())
                                                 .toList();

        Map<Comment, List<Comment>> tree = new HashMap<>(anchors.size());
        anchors.stream()
               .map(anchor -> {
                   var repliesForAnchor = replies.stream()
                                                 .filter(reply -> Objects.equals(anchor.id(), reply.parentId()))
                                                 .toList();
                   return Map.of(anchor, repliesForAnchor);
               })
               .forEach(tree::putAll);

        return tree;
    }
}
