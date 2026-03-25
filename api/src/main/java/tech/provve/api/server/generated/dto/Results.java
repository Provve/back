package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.ResultResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Results {

    private List<ResultResponse> results = new ArrayList<>();
    private Pagination pagination;

    public Results() {

    }

    public Results(List<ResultResponse> results, Pagination pagination) {
        this.results = results;
        this.pagination = pagination;
    }


    @JsonProperty("results")
    public List<ResultResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultResponse> results) {
        this.results = results;
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
        Results results = (Results) o;
        return Objects.equals(results, results.results) &&
               Objects.equals(pagination, results.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Results {\n");

        sb.append("    results: ")
          .append(toIndentedString(results))
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
