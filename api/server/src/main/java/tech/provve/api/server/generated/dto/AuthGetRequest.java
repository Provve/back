package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthGetRequest {

    private String login;

    private String password;

    public AuthGetRequest() {

    }

    public AuthGetRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }


    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthGetRequest authGetRequest = (AuthGetRequest) o;
        return Objects.equals(login, authGetRequest.login) &&
                Objects.equals(password, authGetRequest.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthGetRequest {\n");

        sb.append("    login: ")
          .append(toIndentedString(login))
          .append("\n");
        sb.append("    password: ")
          .append(toIndentedString(password))
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
