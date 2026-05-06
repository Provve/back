package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Краткая информация о профиле
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfilePublicView {

    private String username;
    private String avatarUrl;
    private String contactInfo;

    public ProfilePublicView() {

    }

    public ProfilePublicView(String username, String avatarUrl, String contactInfo) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.contactInfo = contactInfo;
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


    @JsonProperty("contact_info")
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfilePublicView profilePublicView = (ProfilePublicView) o;
        return Objects.equals(username, profilePublicView.username) &&
               Objects.equals(avatarUrl, profilePublicView.avatarUrl) &&
               Objects.equals(contactInfo, profilePublicView.contactInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, avatarUrl, contactInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProfilePublicView {\n");

        sb.append("    username: ")
          .append(toIndentedString(username))
          .append("\n");
        sb.append("    avatarUrl: ")
          .append(toIndentedString(avatarUrl))
          .append("\n");
        sb.append("    contactInfo: ")
          .append(toIndentedString(contactInfo))
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
