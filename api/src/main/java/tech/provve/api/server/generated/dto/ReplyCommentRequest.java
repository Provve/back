package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyCommentRequest {

    private Integer id;
    private Integer targetId;
    private String comment;
    private String authToken;

    public ReplyCommentRequest() {

    }

    public ReplyCommentRequest(Integer id, Integer targetId, String comment, String authToken) {
        this.id = id;
        this.targetId = targetId;
        this.comment = comment;
        this.authToken = authToken;
    }


    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @JsonProperty("target_id")
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }


    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReplyCommentRequest replyCommentRequest = (ReplyCommentRequest) o;
        return Objects.equals(id, replyCommentRequest.id) &&
               Objects.equals(targetId, replyCommentRequest.targetId) &&
               Objects.equals(comment, replyCommentRequest.comment) &&
               Objects.equals(authToken, replyCommentRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targetId, comment, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ReplyCommentRequest {\n");

        sb.append("    id: ")
          .append(toIndentedString(id))
          .append("\n");
        sb.append("    targetId: ")
          .append(toIndentedString(targetId))
          .append("\n");
        sb.append("    comment: ")
          .append(toIndentedString(comment))
          .append("\n");
        sb.append("    authToken: ")
          .append(toIndentedString(authToken))
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
