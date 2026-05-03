package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionRequest {
  
  private String examName;
  private URI redirect;
  private String authToken;

    public CreateSessionRequest() {

  }

    public CreateSessionRequest(String examName, URI redirect, String authToken) {
    this.examName = examName;
    this.redirect = redirect;
    this.authToken = authToken;
  }


    @JsonProperty("exam_name")
  public String getExamName() {
    return examName;
  }
  public void setExamName(String examName) {
    this.examName = examName;
  }


    @JsonProperty("redirect")
  public URI getRedirect() {
    return redirect;
  }
  public void setRedirect(URI redirect) {
    this.redirect = redirect;
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
    CreateSessionRequest createSessionRequest = (CreateSessionRequest) o;
    return Objects.equals(examName, createSessionRequest.examName) &&
           Objects.equals(redirect, createSessionRequest.redirect) &&
           Objects.equals(authToken, createSessionRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(examName, redirect, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateSessionRequest {\n");

      sb.append("    examName: ")
        .append(toIndentedString(examName))
        .append("\n");
      sb.append("    redirect: ")
        .append(toIndentedString(redirect))
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
