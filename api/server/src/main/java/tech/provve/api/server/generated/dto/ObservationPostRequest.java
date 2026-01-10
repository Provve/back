package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.ObservationPostRequestObservation;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationPostRequest {

    private ObservationPostRequestObservation observation;

    private String mac;

    public ObservationPostRequest() {

    }

    public ObservationPostRequest(ObservationPostRequestObservation observation, String mac) {
        this.observation = observation;
        this.mac = mac;
    }


    @JsonProperty("observation")
    public ObservationPostRequestObservation getObservation() {
        return observation;
    }

    public void setObservation(ObservationPostRequestObservation observation) {
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
        ObservationPostRequest observationPostRequest = (ObservationPostRequest) o;
        return Objects.equals(observation, observationPostRequest.observation) &&
                Objects.equals(mac, observationPostRequest.mac);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observation, mac);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ObservationPostRequest {\n");

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
