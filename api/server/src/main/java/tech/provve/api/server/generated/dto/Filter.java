package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filter {

    private String field;

    private FilterPredicate predicate;

    public Filter() {

    }

    public Filter(String field, FilterPredicate predicate) {
        this.field = field;
        this.predicate = predicate;
    }


    @JsonProperty("field")
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    @JsonProperty("predicate")
    public FilterPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(FilterPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Filter filter = (Filter) o;
        return Objects.equals(field, filter.field) &&
                Objects.equals(predicate, filter.predicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, predicate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Filter {\n");

        sb.append("    field: ")
          .append(toIndentedString(field))
          .append("\n");
        sb.append("    predicate: ")
          .append(toIndentedString(predicate))
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
