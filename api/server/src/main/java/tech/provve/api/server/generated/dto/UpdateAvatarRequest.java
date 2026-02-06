package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.FileUpload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateAvatarRequest {

    private FileUpload avatar;

    private String authToken;

    public UpdateAvatarRequest() {

    }

    public UpdateAvatarRequest(FileUpload avatar, String authToken) {
        this.avatar = avatar;
        this.authToken = authToken;
    }


    @JsonProperty("avatar")
    public FileUpload getAvatar() {
        return avatar;
    }

    public void setAvatar(FileUpload avatar) {
        this.avatar = avatar;
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
        UpdateAvatarRequest updateAvatarRequest = (UpdateAvatarRequest) o;
        return Objects.equals(avatar, updateAvatarRequest.avatar) &&
                Objects.equals(authToken, updateAvatarRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatar, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateAvatarRequest {\n");

        sb.append("    avatar: ")
          .append(toIndentedString(avatar))
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
