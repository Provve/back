package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest {

    private String resetCode;

    private String newPasswordHash;

    public UpdatePasswordRequest() {

    }

    public UpdatePasswordRequest(String resetCode, String newPasswordHash) {
        this.resetCode = resetCode;
        this.newPasswordHash = newPasswordHash;
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
                Objects.equals(newPasswordHash, updatePasswordRequest.newPasswordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resetCode, newPasswordHash);
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
