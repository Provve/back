package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Успешный результат
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {
  
  private String examName;
  private Long time;

    public ResultResponse() {

  }

    public ResultResponse(String examName, Long time) {
    this.examName = examName;
    this.time = time;
  }


    @JsonProperty("exam_name")
  public String getExamName() {
    return examName;
  }
  public void setExamName(String examName) {
    this.examName = examName;
  }


    @JsonProperty("time")
  public Long getTime() {
    return time;
  }
  public void setTime(Long time) {
    this.time = time;
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
           Objects.equals(time, resultResponse.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(examName, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultResponse {\n");

      sb.append("    examName: ")
        .append(toIndentedString(examName))
        .append("\n");
      sb.append("    time: ")
        .append(toIndentedString(time))
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
