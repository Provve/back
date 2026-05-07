package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfilePrivateView {

    private String login;
    private String avatarUrl;
    private String contactInfo;
    private List<String> interests = new ArrayList<>();
    private String email;
    private Boolean isConsentPersonalData;
    private Boolean isPremium;

    public ProfilePrivateView() {

    }

    public ProfilePrivateView(String login,
                              String avatarUrl,
                              String contactInfo,
                              List<String> interests,
                              String email,
                              Boolean isConsentPersonalData,
                              Boolean isPremium) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.contactInfo = contactInfo;
        this.interests = interests;
        this.email = email;
        this.isConsentPersonalData = isConsentPersonalData;
        this.isPremium = isPremium;
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


    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @JsonProperty("is_consent_personal_data")
    public Boolean getIsConsentPersonalData() {
        return isConsentPersonalData;
    }

    public void setIsConsentPersonalData(Boolean isConsentPersonalData) {
        this.isConsentPersonalData = isConsentPersonalData;
    }


    @JsonProperty("is_premium")
    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfilePrivateView profilePrivateView = (ProfilePrivateView) o;
        return Objects.equals(login, profilePrivateView.login) &&
               Objects.equals(avatarUrl, profilePrivateView.avatarUrl) &&
               Objects.equals(contactInfo, profilePrivateView.contactInfo) &&
               Objects.equals(interests, profilePrivateView.interests) &&
               Objects.equals(email, profilePrivateView.email) &&
               Objects.equals(isConsentPersonalData, profilePrivateView.isConsentPersonalData) &&
               Objects.equals(isPremium, profilePrivateView.isPremium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, avatarUrl, contactInfo, interests, email, isConsentPersonalData, isPremium);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProfilePrivateView {\n");

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
        sb.append("    email: ")
          .append(toIndentedString(email))
          .append("\n");
        sb.append("    isConsentPersonalData: ")
          .append(toIndentedString(isConsentPersonalData))
          .append("\n");
        sb.append("    isPremium: ")
          .append(toIndentedString(isPremium))
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
