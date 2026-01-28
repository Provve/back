package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.ProfileResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    private ProfileResponse author;

    private String comment;

    public CommentResponse() {

    }

    public CommentResponse(ProfileResponse author, String comment) {
        this.author = author;
        this.comment = comment;
    }


    @JsonProperty("author")
    public ProfileResponse getAuthor() {
        return author;
    }

    public void setAuthor(ProfileResponse author) {
        this.author = author;
    }


    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentResponse commentResponse = (CommentResponse) o;
        return Objects.equals(author, commentResponse.author) &&
                Objects.equals(comment, commentResponse.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, comment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentResponse {\n");

        sb.append("    author: ")
          .append(toIndentedString(author))
          .append("\n");
        sb.append("    comment: ")
          .append(toIndentedString(comment))
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
