package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationUploadRequest {
  
  private Observation observation;
  private String nonce;
  private String sig;
  private String authToken;

  public ObservationUploadRequest() {

  }

  public ObservationUploadRequest(Observation observation, String nonce, String sig, String authToken) {
    this.observation = observation;
    this.nonce = nonce;
    this.sig = sig;
    this.authToken = authToken;
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


  @JsonProperty("auth_token")
  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObservationUploadRequest observationUploadRequest = (ObservationUploadRequest) o;
    return Objects.equals(observation, observationUploadRequest.observation) &&
           Objects.equals(nonce, observationUploadRequest.nonce) &&
           Objects.equals(sig, observationUploadRequest.sig) &&
           Objects.equals(authToken, observationUploadRequest.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(observation, nonce, sig, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ObservationUploadRequest {\n");

    sb.append("    observation: ")
      .append(toIndentedString(observation))
      .append("\n");
    sb.append("    nonce: ")
      .append(toIndentedString(nonce))
      .append("\n");
    sb.append("    sig: ")
      .append(toIndentedString(sig))
      .append("\n");
    sb.append("    authToken: ")
      .append(toIndentedString(authToken))
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
