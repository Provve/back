package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Examinee {

    private String examName;
    private Long durationMinutes;
    private String login;
    private String avatarUrl;
    private String contactInfo;
    private List<String> interests = new ArrayList<>();

    public Examinee() {

    }

    public Examinee(String examName, Long durationMinutes, String login, String avatarUrl, String contactInfo, List<String> interests) {
        this.examName = examName;
        this.durationMinutes = durationMinutes;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.contactInfo = contactInfo;
        this.interests = interests;
    }


    @JsonProperty("exam_name")
    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }


    @JsonProperty("duration_minutes")
    public Long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
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
        Examinee examinee = (Examinee) o;
        return Objects.equals(examName, examinee.examName) &&
               Objects.equals(durationMinutes, examinee.durationMinutes) &&
               Objects.equals(login, examinee.login) &&
               Objects.equals(avatarUrl, examinee.avatarUrl) &&
               Objects.equals(contactInfo, examinee.contactInfo) &&
               Objects.equals(interests, examinee.interests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examName, durationMinutes, login, avatarUrl, contactInfo, interests);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Examinee {\n");

        sb.append("    examName: ")
          .append(toIndentedString(examName))
          .append("\n");
        sb.append("    durationMinutes: ")
          .append(toIndentedString(durationMinutes))
          .append("\n");
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
