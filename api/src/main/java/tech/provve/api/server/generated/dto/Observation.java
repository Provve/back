package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Observation {

    private Boolean cheated;
    private String violations;

    public Observation() {

    }

    public Observation(Boolean cheated, String violations) {
        this.cheated = cheated;
        this.violations = violations;
    }


    @JsonProperty("cheated")
    public Boolean getCheated() {
        return cheated;
    }

    public void setCheated(Boolean cheated) {
        this.cheated = cheated;
    }


    @JsonProperty("violations")
    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
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
        return Objects.equals(cheated, observation.cheated) &&
               Objects.equals(violations, observation.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cheated, violations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Observation {\n");

        sb.append("    cheated: ")
          .append(toIndentedString(cheated))
          .append("\n");
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
}
