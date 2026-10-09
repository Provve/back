package tech.provve.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionRequest {

    private String skillName;
    private URI redirect;
    private String authToken;

    public CreateSessionRequest() {

    }

    public CreateSessionRequest(String skillName, URI redirect, String authToken) {
        this.skillName = skillName;
        this.redirect = redirect;
        this.authToken = authToken;
    }


    @JsonProperty("skill_name")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
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
        return Objects.equals(skillName, createSessionRequest.skillName) &&
                Objects.equals(redirect, createSessionRequest.redirect) &&
                Objects.equals(authToken, createSessionRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillName, redirect, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateSessionRequest {\n");

        sb.append("    skillName: ")
          .append(toIndentedString(skillName))
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
