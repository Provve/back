package tech.provve.skill.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.provve.constants.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mapper
public interface CommentRepository {

    String COMMENT = "comment";

    @Insert("""
            INSERT INTO skill.comment (author, content, vote_name, parent_id)
            VALUES (
                #{comment.author, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{comment.content, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{comment.voteName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{comment.parentId, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            )
            """)
    void save(@Param("comment") Map<String, Object> comment);

    @Update("""
            UPDATE skill.comment
            SET content = #{content, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            WHERE id = #{id, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            """)
    void update(@Param("id") Integer id, @Param("content") String content);

    @Delete("""
            DELETE FROM skill.comment
            WHERE id = #{id, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            """)
    void delete(@Param("id") Integer id);

    @Select("""
            SELECT id, author, content, created, vote_name, parent_id
            FROM skill.comment
            WHERE id = #{id, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            """)
    @Results(id = COMMENT, value = {
            @Result(property = Entity.Comment.ID, column = "id", typeHandler = org.apache.ibatis.type.IntegerTypeHandler.class),
            @Result(property = Entity.Comment.AUTHOR, column = "author", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Comment.CONTENT, column = "content", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Comment.CREATED, column = "created", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = Entity.Comment.VOTE_NAME, column = "vote_name", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Comment.PARENT_ID, column = "parent_id", typeHandler = org.apache.ibatis.type.IntegerTypeHandler.class)
    })
    Optional<Map<String, Object>> get(@Param("id") Integer id);

    @Select("""
            SELECT id, author, content, created, vote_name, parent_id
            FROM skill.get_comments_tree(
                COALESCE(CAST(NULLIF(#{previous, typeHandler=org.apache.ibatis.type.StringTypeHandler}, '') AS INTEGER), 0),
                #{voteName, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{pageSize, typeHandler=org.apache.ibatis.type.IntegerTypeHandler}
            )
            """)
    @ResultMap(COMMENT)
    List<Map<String, Object>> selectCommentTree(@Param("previous") String previous,
                                                @Param("pageSize") int pageSize,
                                                @Param("voteName") String voteName);

    /**
     * @return key — anchror <br>
     * value — list of replies
     */
    default Map<Map<String, Object>, List<Map<String, Object>>> getAll(String previous, int pageSize, String voteName) {
        List<Map<String, Object>> anchorsAndReplies = selectCommentTree(previous, pageSize, voteName);
        List<Map<String, Object>> anchors = anchorsAndReplies.stream()
                                                             .filter(comment -> null == comment.get(Entity.Comment.PARENT_ID))
                                                             .toList();
        List<Map<String, Object>> replies = anchorsAndReplies.stream()
                                                             .filter(comment -> null != comment.get(Entity.Comment.PARENT_ID))
                                                             .toList();

        Map<Map<String, Object>, List<Map<String, Object>>> tree = HashMap.newHashMap(anchors.size());
        anchors.forEach(anchor -> {
            List<Map<String, Object>> repliesForAnchor = replies.stream()
                                                                .filter(reply -> Objects.equals(
                                                                        anchor.get(Entity.Comment.ID),
                                                                        reply.get(Entity.Comment.PARENT_ID)
                                                                ))
                                                                .toList();
            tree.put(anchor, repliesForAnchor);
        });
        return tree;
    }

}
