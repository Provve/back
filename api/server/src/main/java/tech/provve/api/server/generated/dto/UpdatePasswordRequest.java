package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest {

    private String resetToken;

    private String newPasswordHash;

    public UpdatePasswordRequest() {

    }

    public UpdatePasswordRequest(String resetToken, String newPasswordHash) {
        this.resetToken = resetToken;
        this.newPasswordHash = newPasswordHash;
    }


    @JsonProperty("reset_token")
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }


    @JsonProperty("new_password_hash")
    public String getNewPasswordHash() {
        return newPasswordHash;
    }

    public void setNewPasswordHash(String newPasswordHash) {
        this.newPasswordHash = newPasswordHash;
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
        return Objects.equals(resetToken, updatePasswordRequest.resetToken) &&
                Objects.equals(newPasswordHash, updatePasswordRequest.newPasswordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetToken, newPasswordHash);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdatePasswordRequest {\n");

        sb.append("    resetToken: ")
          .append(toIndentedString(resetToken))
          .append("\n");
        sb.append("    newPasswordHash: ")
          .append(toIndentedString(newPasswordHash))
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
