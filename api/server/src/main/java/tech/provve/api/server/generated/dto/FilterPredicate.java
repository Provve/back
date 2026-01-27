package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterPredicate {


    public enum OperatorEnum {
        NOT("NOT"),
        EQ("EQ"),
        NOT_EQ("NOT_EQ"),
        AND("AND"),
        OR("OR"),
        LIKE("LIKE");

        private String value;

        OperatorEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    private OperatorEnum operator;

    private String value;

    public FilterPredicate() {

    }

    public FilterPredicate(OperatorEnum operator, String value) {
        this.operator = operator;
        this.value = value;
    }


    @JsonProperty("operator")
    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }


    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilterPredicate filterPredicate = (FilterPredicate) o;
        return Objects.equals(operator, filterPredicate.operator) &&
                Objects.equals(value, filterPredicate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FilterPredicate {\n");

        sb.append("    operator: ")
          .append(toIndentedString(operator))
          .append("\n");
        sb.append("    value: ")
          .append(toIndentedString(value))
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
