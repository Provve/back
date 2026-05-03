package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationUploadResponse {

    private String trustToken;

    public ObservationUploadResponse() {

    }

    public ObservationUploadResponse(String trustToken) {
        this.trustToken = trustToken;
    }


    @JsonProperty("trust_token")
    public String getTrustToken() {
        return trustToken;
    }

    public void setTrustToken(String trustToken) {
        this.trustToken = trustToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObservationUploadResponse observationUploadResponse = (ObservationUploadResponse) o;
        return Objects.equals(trustToken, observationUploadResponse.trustToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trustToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ObservationUploadResponse {\n");

        sb.append("    trustToken: ")
          .append(toIndentedString(trustToken))
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
