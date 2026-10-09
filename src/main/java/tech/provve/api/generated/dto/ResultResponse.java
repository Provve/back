package tech.provve.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Успешный результат
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {

    private String skillName;
    private Long durationMinutes;
    private Boolean success;
    private OffsetDateTime createdAt;

    public ResultResponse() {

    }

    public ResultResponse(String skillName, Long durationMinutes, Boolean success, OffsetDateTime createdAt) {
        this.skillName = skillName;
        this.durationMinutes = durationMinutes;
        this.success = success;
        this.createdAt = createdAt;
    }


    @JsonProperty("skill_name")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }


    @JsonProperty("duration_minutes")
    public Long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }


    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultResponse resultResponse = (ResultResponse) o;
        return Objects.equals(skillName, resultResponse.skillName) &&
                Objects.equals(durationMinutes, resultResponse.durationMinutes) &&
                Objects.equals(success, resultResponse.success) &&
                Objects.equals(createdAt, resultResponse.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillName, durationMinutes, success, createdAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResultResponse {\n");

        sb.append("    skillName: ")
          .append(toIndentedString(skillName))
          .append("\n");
        sb.append("    durationMinutes: ")
          .append(toIndentedString(durationMinutes))
          .append("\n");
        sb.append("    success: ")
          .append(toIndentedString(success))
          .append("\n");
        sb.append("    createdAt: ")
          .append(toIndentedString(createdAt))
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
