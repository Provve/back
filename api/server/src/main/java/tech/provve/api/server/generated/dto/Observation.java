package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Observation {


    private List<ViolationsEnum> violations = new ArrayList<>();

    public Observation() {

    }

    public Observation(List<ViolationsEnum> violations) {
        this.violations = violations;
    }

    @JsonProperty("violations")
    public List<ViolationsEnum> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationsEnum> violations) {
        this.violations = violations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Observation observation = (Observation) o;
        return Objects.equals(violations, observation.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(violations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Observation {\n");

        sb.append("    violations: ")
          .append(toIndentedString(violations))
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

    public enum ViolationsEnum {
        REMOTE_CONTROL("remote_control"),
        CLIPBOARD("clipboard"),
        WEB("web");

        private String value;

        ViolationsEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
