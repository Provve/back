package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyCommentRequest {

  private Integer targetId;
  private String content;
  private String authToken;

  public ReplyCommentRequest() {

  }

  public ReplyCommentRequest(Integer targetId, String content, String authToken) {
    this.targetId = targetId;
    this.content = content;
    this.authToken = authToken;
  }


  @JsonProperty("target_id")
  public Integer getTargetId() {
    return targetId;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }


  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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
    return Objects.equals(targetId, replyCommentRequest.targetId) &&
           Objects.equals(content, replyCommentRequest.content) &&
           Objects.equals(authToken, replyCommentRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetId, content, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReplyCommentRequest {\n");

    sb.append("    targetId: ")
      .append(toIndentedString(targetId))
      .append("\n");
    sb.append("    content: ")
      .append(toIndentedString(content))
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
