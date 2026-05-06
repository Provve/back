package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {

    private String username;
    private String avatarUrl;

    public ProfileResponse() {

    }

    public ProfileResponse(String username, String avatarUrl) {
        this.username = username;
        this.avatarUrl = avatarUrl;
    }


    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @JsonProperty("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileResponse profileResponse = (ProfileResponse) o;
        return Objects.equals(username, profileResponse.username) &&
               Objects.equals(avatarUrl, profileResponse.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, avatarUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProfileResponse {\n");

        sb.append("    username: ")
          .append(toIndentedString(username))
          .append("\n");
        sb.append("    avatarUrl: ")
          .append(toIndentedString(avatarUrl))
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
