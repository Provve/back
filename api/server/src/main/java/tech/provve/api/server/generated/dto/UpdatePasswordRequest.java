package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest {

    private String resetCode;

    private String newPassword;

    private String login;

    public UpdatePasswordRequest() {

    }

    public UpdatePasswordRequest(String resetCode, String newPassword, String login) {
        this.resetCode = resetCode;
        this.newPassword = newPassword;
        this.login = login;
    }


    @JsonProperty("reset_code")
    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }


    @JsonProperty("new_password")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdatePasswordRequest updatePasswordRequest = (UpdatePasswordRequest) o;
        return Objects.equals(resetCode, updatePasswordRequest.resetCode) &&
                Objects.equals(newPassword, updatePasswordRequest.newPassword) &&
                Objects.equals(login, updatePasswordRequest.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetCode, newPassword, login);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdatePasswordRequest {\n");

        sb.append("    resetCode: ")
          .append(toIndentedString(resetCode))
          .append("\n");
        sb.append("    newPassword: ")
          .append(toIndentedString(newPassword))
          .append("\n");
        sb.append("    login: ")
          .append(toIndentedString(login))
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
