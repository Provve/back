package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationUpload {

    private Boolean cheated;
    private String details;
    private String sig;
    private Checksum checksum;

    public ObservationUpload() {

    }

    public ObservationUpload(Boolean cheated, String details, String sig, Checksum checksum) {
        this.cheated = cheated;
        this.details = details;
        this.sig = sig;
        this.checksum = checksum;
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


    @JsonProperty("sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }


    @JsonProperty("checksum")
    public Checksum getChecksum() {
        return checksum;
    }

    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
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
        return Objects.equals(cheated, observationUpload.cheated) &&
                Objects.equals(details, observationUpload.details) &&
                Objects.equals(sig, observationUpload.sig) &&
                Objects.equals(checksum, observationUpload.checksum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cheated, details, sig, checksum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ObservationUpload {\n");

        sb.append("    cheated: ")
          .append(toIndentedString(cheated))
          .append("\n");
        sb.append("    details: ")
          .append(toIndentedString(details))
          .append("\n");
        sb.append("    sig: ")
          .append(toIndentedString(sig))
          .append("\n");
        sb.append("    checksum: ")
          .append(toIndentedString(checksum))
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
