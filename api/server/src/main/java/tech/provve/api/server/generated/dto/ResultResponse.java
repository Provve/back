package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {

    private java.util.UUID examId;

    private java.util.UUID examName;

    public ResultResponse() {

    }

    public ResultResponse(java.util.UUID examId, java.util.UUID examName) {
        this.examId = examId;
        this.examName = examName;
    }


    @JsonProperty("exam_id")
    public java.util.UUID getExamId() {
        return examId;
    }

    public void setExamId(java.util.UUID examId) {
        this.examId = examId;
    }


    @JsonProperty("exam_name")
    public java.util.UUID getExamName() {
        return examName;
    }

    public void setExamName(java.util.UUID examName) {
        this.examName = examName;
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
        return Objects.equals(examId, resultResponse.examId) &&
                Objects.equals(examName, resultResponse.examName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, examName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResultResponse {\n");

        sb.append("    examId: ")
          .append(toIndentedString(examId))
          .append("\n");
        sb.append("    examName: ")
          .append(toIndentedString(examName))
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
