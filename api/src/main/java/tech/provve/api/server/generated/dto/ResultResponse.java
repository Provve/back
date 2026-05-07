package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Успешный результат
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {
  
  private String examName;
  private Long durationMinutes;

  public ResultResponse() {

  }

  public ResultResponse(String examName, Long durationMinutes) {
    this.examName = examName;
    this.durationMinutes = durationMinutes;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultResponse resultResponse = (ResultResponse) o;
    return Objects.equals(examName, resultResponse.examName) &&
           Objects.equals(durationMinutes, resultResponse.durationMinutes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(examName, durationMinutes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultResponse {\n");

    sb.append("    examName: ")
      .append(toIndentedString(examName))
      .append("\n");
    sb.append("    durationMinutes: ")
      .append(toIndentedString(durationMinutes))
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
