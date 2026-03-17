package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionResponse {

  private URI redirect;
  private Boolean lossRisk;
  private String nonce;

  public CreateSessionResponse() {

  }

  public CreateSessionResponse(URI redirect, Boolean lossRisk, String nonce) {
    this.redirect = redirect;
    this.lossRisk = lossRisk;
    this.nonce = nonce;
  }


  @JsonProperty("redirect")
  public URI getRedirect() {
    return redirect;
  }

  public void setRedirect(URI redirect) {
    this.redirect = redirect;
  }


  @JsonProperty("loss_risk")
  public Boolean getLossRisk() {
    return lossRisk;
  }

  public void setLossRisk(Boolean lossRisk) {
    this.lossRisk = lossRisk;
  }


  @JsonProperty("nonce")
  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateSessionResponse createSessionResponse = (CreateSessionResponse) o;
    return Objects.equals(redirect, createSessionResponse.redirect) &&
            Objects.equals(lossRisk, createSessionResponse.lossRisk) &&
            Objects.equals(nonce, createSessionResponse.nonce);
  }

  @Override
  public int hashCode() {
    return Objects.hash(redirect, lossRisk, nonce);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateSessionResponse {\n");

    sb.append("    redirect: ")
      .append(toIndentedString(redirect))
      .append("\n");
    sb.append("    lossRisk: ")
      .append(toIndentedString(lossRisk))
      .append("\n");
    sb.append("    nonce: ")
      .append(toIndentedString(nonce))
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
