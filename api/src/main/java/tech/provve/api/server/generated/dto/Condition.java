package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition {

    private String field;


    public enum OperatorEnum {
        EQ("EQ"),
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

    public Condition() {

    }

    public Condition(String field, OperatorEnum operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }


    @JsonProperty("field")
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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
        Condition condition = (Condition) o;
        return Objects.equals(field, condition.field) &&
               Objects.equals(operator, condition.operator) &&
               Objects.equals(value, condition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, operator, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Condition {\n");

        sb.append("    field: ")
          .append(toIndentedString(field))
          .append("\n");
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
