package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCommentRequest {
  
  private String voteName;
  private String content;
  private String authToken;

  public AddCommentRequest() {

  }

  public AddCommentRequest(String voteName, String content, String authToken) {
    this.voteName = voteName;
    this.content = content;
    this.authToken = authToken;
  }


  @JsonProperty("vote_name")
  public String getVoteName() {
    return voteName;
  }
  public void setVoteName(String voteName) {
    this.voteName = voteName;
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
    AddCommentRequest addCommentRequest = (AddCommentRequest) o;
    return Objects.equals(voteName, addCommentRequest.voteName) &&
           Objects.equals(content, addCommentRequest.content) &&
           Objects.equals(authToken, addCommentRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voteName, content, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCommentRequest {\n");

    sb.append("    voteName: ")
      .append(toIndentedString(voteName))
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
