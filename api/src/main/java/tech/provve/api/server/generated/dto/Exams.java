package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exams {

    private List<ExamResponse> exams = new ArrayList<>();
    private Pagination pagination;

    public Exams() {

    }

    public Exams(List<ExamResponse> exams, Pagination pagination) {
        this.exams = exams;
        this.pagination = pagination;
    }


    @JsonProperty("exams")
    public List<ExamResponse> getExams() {
        return exams;
    }

    public void setExams(List<ExamResponse> exams) {
        this.exams = exams;
    }


    @JsonProperty("pagination")
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exams exams = (Exams) o;
        return Objects.equals(exams, exams.exams) &&
                Objects.equals(pagination, exams.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exams, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Exams {\n");

        sb.append("    exams: ")
          .append(toIndentedString(exams))
          .append("\n");
        sb.append("    pagination: ")
          .append(toIndentedString(pagination))
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
