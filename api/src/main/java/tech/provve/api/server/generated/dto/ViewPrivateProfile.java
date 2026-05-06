package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewPrivateProfile {

    private String login;
    private String authToken;

    public ViewPrivateProfile() {

    }

    public ViewPrivateProfile(String login, String authToken) {
        this.login = login;
        this.authToken = authToken;
    }


    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        ViewPrivateProfile viewPrivateProfile = (ViewPrivateProfile) o;
        return Objects.equals(login, viewPrivateProfile.login) &&
               Objects.equals(authToken, viewPrivateProfile.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ViewPrivateProfile {\n");

        sb.append("    login: ")
          .append(toIndentedString(login))
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
