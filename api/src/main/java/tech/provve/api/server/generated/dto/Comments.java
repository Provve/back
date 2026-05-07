package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.Cursor;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comments {

    private List<CommentResponse> comments = new ArrayList<>();
    private Cursor cursor;

    public Comments() {

    }

    public Comments(List<CommentResponse> comments, Cursor cursor) {
        this.comments = comments;
        this.cursor = cursor;
    }


    @JsonProperty("comments")
    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }


    @JsonProperty("cursor")
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comments comments = (Comments) o;
        return Objects.equals(comments, comments.comments) &&
               Objects.equals(cursor, comments.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comments, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Comments {\n");

        sb.append("    comments: ")
          .append(toIndentedString(comments))
          .append("\n");
        sb.append("    cursor: ")
          .append(toIndentedString(cursor))
          .append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString()
                .replace("\n", "\n    ");
    }
}
