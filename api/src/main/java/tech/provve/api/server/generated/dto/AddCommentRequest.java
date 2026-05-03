package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCommentRequest {

  private String comment;
  private String authToken;

  public AddCommentRequest() {

  }

  public AddCommentRequest(String comment, String authToken) {
    this.comment = comment;
    this.authToken = authToken;
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
    AddCommentRequest addCommentRequest = (AddCommentRequest) o;
    return Objects.equals(comment, addCommentRequest.comment) &&
           Objects.equals(authToken, addCommentRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCommentRequest {\n");

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
