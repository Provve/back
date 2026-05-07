package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Краткая информация о профиле
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfilePublicView {

    private String login;
    private String avatarUrl;
    private String contactInfo;
    private List<String> interests = new ArrayList<>();

    public ProfilePublicView() {

    }

    public ProfilePublicView(String login, String avatarUrl, String contactInfo, List<String> interests) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.contactInfo = contactInfo;
        this.interests = interests;
    }


    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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


    @JsonProperty("interests")
    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
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
        return Objects.equals(login, profilePublicView.login) &&
               Objects.equals(avatarUrl, profilePublicView.avatarUrl) &&
               Objects.equals(contactInfo, profilePublicView.contactInfo) &&
               Objects.equals(interests, profilePublicView.interests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, avatarUrl, contactInfo, interests);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProfilePublicView {\n");

        sb.append("    login: ")
          .append(toIndentedString(login))
          .append("\n");
        sb.append("    avatarUrl: ")
          .append(toIndentedString(avatarUrl))
          .append("\n");
        sb.append("    contactInfo: ")
          .append(toIndentedString(contactInfo))
          .append("\n");
        sb.append("    interests: ")
          .append(toIndentedString(interests))
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
