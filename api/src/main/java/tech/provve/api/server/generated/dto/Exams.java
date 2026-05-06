package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.ExamResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exams {

    private List<ExamResponse> exams = new ArrayList<>();
    private Cursor cursor;

    public Exams() {

    }

    public Exams(List<ExamResponse> exams, Cursor cursor) {
        this.exams = exams;
        this.cursor = cursor;
    }


    @JsonProperty("exams")
    public List<ExamResponse> getExams() {
        return exams;
    }

    public void setExams(List<ExamResponse> exams) {
        this.exams = exams;
    }


    @JsonProperty("cursor")
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
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
               Objects.equals(cursor, exams.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exams, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Exams {\n");

        sb.append("    exams: ")
          .append(toIndentedString(exams))
          .append("\n");
        sb.append("    cursor: ")
          .append(toIndentedString(cursor))
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
