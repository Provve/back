package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.Examinee;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Examinees {

    private List<Examinee> examinees = new ArrayList<>();
    private Cursor cursor;

    public Examinees() {

    }

    public Examinees(List<Examinee> examinees, Cursor cursor) {
        this.examinees = examinees;
        this.cursor = cursor;
    }


    @JsonProperty("examinees")
    public List<Examinee> getExaminees() {
        return examinees;
    }

    public void setExaminees(List<Examinee> examinees) {
        this.examinees = examinees;
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
        Examinees examinees = (Examinees) o;
        return Objects.equals(examinees, examinees.examinees) &&
               Objects.equals(cursor, examinees.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examinees, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Examinees {\n");

        sb.append("    examinees: ")
          .append(toIndentedString(examinees))
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
