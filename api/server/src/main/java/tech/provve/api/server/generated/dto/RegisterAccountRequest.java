package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterAccountRequest {

    private String login;

    private String email;

    private String password;

    private Boolean consentPersonalData;

    private String username;

    public RegisterAccountRequest() {

    }

    public RegisterAccountRequest(String login, String email, String password, Boolean consentPersonalData, String username) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.consentPersonalData = consentPersonalData;
        this.username = username;
    }


    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @JsonProperty("consent_personal_data")
    public Boolean getConsentPersonalData() {
        return consentPersonalData;
    }

    public void setConsentPersonalData(Boolean consentPersonalData) {
        this.consentPersonalData = consentPersonalData;
    }


    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisterAccountRequest registerAccountRequest = (RegisterAccountRequest) o;
        return Objects.equals(login, registerAccountRequest.login) &&
                Objects.equals(email, registerAccountRequest.email) &&
                Objects.equals(password, registerAccountRequest.password) &&
                Objects.equals(consentPersonalData, registerAccountRequest.consentPersonalData) &&
                Objects.equals(username, registerAccountRequest.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, password, consentPersonalData, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RegisterAccountRequest {\n");

        sb.append("    login: ")
          .append(toIndentedString(login))
          .append("\n");
        sb.append("    email: ")
          .append(toIndentedString(email))
          .append("\n");
        sb.append("    password: ")
          .append(toIndentedString(password))
          .append("\n");
        sb.append("    consentPersonalData: ")
          .append(toIndentedString(consentPersonalData))
          .append("\n");
        sb.append("    username: ")
          .append(toIndentedString(username))
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
