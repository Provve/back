package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Observation {

    private Boolean cheated;
    private String details;

    public Observation() {

    }

    public Observation(Boolean cheated, String details) {
        this.cheated = cheated;
        this.details = details;
    }


    @JsonProperty("cheated")
    public Boolean getCheated() {
        return cheated;
    }

    public void setCheated(Boolean cheated) {
        this.cheated = cheated;
    }


    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
                Objects.equals(details, observation.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cheated, details);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Observation {\n");

        sb.append("    cheated: ")
          .append(toIndentedString(cheated))
          .append("\n");
        sb.append("    details: ")
          .append(toIndentedString(details))
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
