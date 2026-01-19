package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationUpload {

    private Observation observation;

    private String mac;

    public ObservationUpload() {

    }

    public ObservationUpload(Observation observation, String mac) {
        this.observation = observation;
        this.mac = mac;
    }


    @JsonProperty("observation")
    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }


    @JsonProperty("mac")
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObservationUpload observationUpload = (ObservationUpload) o;
        return Objects.equals(observation, observationUpload.observation) &&
                Objects.equals(mac, observationUpload.mac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observation, mac);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ObservationUpload {\n");

        sb.append("    observation: ")
          .append(toIndentedString(observation))
          .append("\n");
        sb.append("    mac: ")
          .append(toIndentedString(mac))
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
