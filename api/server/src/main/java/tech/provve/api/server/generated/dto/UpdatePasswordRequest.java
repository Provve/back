package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest {

    private String resetCode;

    private String newPasswordHash;

    private String login;

    public UpdatePasswordRequest() {

    }

    public UpdatePasswordRequest(String resetCode, String newPasswordHash, String login) {
        this.resetCode = resetCode;
        this.newPasswordHash = newPasswordHash;
        this.login = login;
    }


    @JsonProperty("reset_code")
    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }


    @JsonProperty("new_password_hash")
    public String getNewPasswordHash() {
        return newPasswordHash;
    }

    public void setNewPasswordHash(String newPasswordHash) {
        this.newPasswordHash = newPasswordHash;
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
                Objects.equals(newPasswordHash, updatePasswordRequest.newPasswordHash) &&
                Objects.equals(login, updatePasswordRequest.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetCode, newPasswordHash, login);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdatePasswordRequest {\n");

        sb.append("    resetCode: ")
          .append(toIndentedString(resetCode))
          .append("\n");
        sb.append("    newPasswordHash: ")
          .append(toIndentedString(newPasswordHash))
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
