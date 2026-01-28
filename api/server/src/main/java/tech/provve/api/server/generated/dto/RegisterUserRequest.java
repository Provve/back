package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserRequest {

    private String login;

    private String email;

    private String passwordHash;

    private Boolean consentPersonalData;

    private String username;

    public RegisterUserRequest() {

    }

    public RegisterUserRequest(String login, String email, String passwordHash, Boolean consentPersonalData, String username) {
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
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


    @JsonProperty("password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
        RegisterUserRequest registerUserRequest = (RegisterUserRequest) o;
        return Objects.equals(login, registerUserRequest.login) &&
                Objects.equals(email, registerUserRequest.email) &&
                Objects.equals(passwordHash, registerUserRequest.passwordHash) &&
                Objects.equals(consentPersonalData, registerUserRequest.consentPersonalData) &&
                Objects.equals(username, registerUserRequest.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, passwordHash, consentPersonalData, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RegisterUserRequest {\n");

        sb.append("    login: ")
          .append(toIndentedString(login))
          .append("\n");
        sb.append("    email: ")
          .append(toIndentedString(email))
          .append("\n");
        sb.append("    passwordHash: ")
          .append(toIndentedString(passwordHash))
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
