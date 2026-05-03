package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCommentRequest {

    private String comment;

    public AddCommentRequest() {

    }

    public AddCommentRequest(String comment) {
        this.comment = comment;
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
        AddCommentRequest addCommentRequest = (AddCommentRequest) o;
        return Objects.equals(comment, addCommentRequest.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AddCommentRequest {\n");

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
