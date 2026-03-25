package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.Observation;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationUpload {

    private Observation observation;
    private String nonce;
    private String sig;

    public ObservationUpload() {

    }

    public ObservationUpload(Observation observation, String nonce, String sig) {
        this.observation = observation;
        this.nonce = nonce;
        this.sig = sig;
    }


    @JsonProperty("observation")
    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }


    @JsonProperty("nonce")
    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }


    @JsonProperty("sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
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
               Objects.equals(nonce, observationUpload.nonce) &&
               Objects.equals(sig, observationUpload.sig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observation, nonce, sig);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ObservationUpload {\n");

        sb.append("    observation: ")
          .append(toIndentedString(observation))
          .append("\n");
        sb.append("    nonce: ")
          .append(toIndentedString(nonce))
          .append("\n");
        sb.append("    sig: ")
          .append(toIndentedString(sig))
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
