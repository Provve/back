package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Results {

    private List<ResultResponse> results = new ArrayList<>();
    private Cursor cursor;

    public Results() {

    }

    public Results(List<ResultResponse> results, Cursor cursor) {
        this.results = results;
        this.cursor = cursor;
    }


    @JsonProperty("results")
    public List<ResultResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultResponse> results) {
        this.results = results;
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
        Results results = (Results) o;
        return Objects.equals(results, results.results) &&
               Objects.equals(cursor, results.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Results {\n");

        sb.append("    results: ")
          .append(toIndentedString(results))
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
