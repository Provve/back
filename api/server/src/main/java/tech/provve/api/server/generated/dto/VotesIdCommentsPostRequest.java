package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VotesIdCommentsPostRequest {

    private String comment;

    public VotesIdCommentsPostRequest() {

    }

    public VotesIdCommentsPostRequest(String comment) {
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
        VotesIdCommentsPostRequest votesIdCommentsPostRequest = (VotesIdCommentsPostRequest) o;
        return Objects.equals(comment, votesIdCommentsPostRequest.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VotesIdCommentsPostRequest {\n");

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
