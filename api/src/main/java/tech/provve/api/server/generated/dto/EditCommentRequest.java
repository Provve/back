package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditCommentRequest {
  
  private Integer id;
  private String content;
  private String authToken;

  public EditCommentRequest() {

  }

  public EditCommentRequest(Integer id, String content, String authToken) {
    this.id = id;
    this.content = content;
    this.authToken = authToken;
  }


  @JsonProperty("id")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
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
    EditCommentRequest editCommentRequest = (EditCommentRequest) o;
    return Objects.equals(id, editCommentRequest.id) &&
           Objects.equals(content, editCommentRequest.content) &&
           Objects.equals(authToken, editCommentRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EditCommentRequest {\n");

    sb.append("    id: ")
      .append(toIndentedString(id))
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
