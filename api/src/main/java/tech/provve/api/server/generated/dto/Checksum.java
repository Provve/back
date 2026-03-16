package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Checksum {

    private String checksum;
    private String sig;

    public Checksum() {

    }

    public Checksum(String checksum, String sig) {
        this.checksum = checksum;
        this.sig = sig;
    }


    @JsonProperty("checksum")
    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
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
        Checksum checksum = (Checksum) o;
        return Objects.equals(checksum, checksum.checksum) &&
                Objects.equals(sig, checksum.sig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, sig);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Checksum {\n");

        sb.append("    checksum: ")
          .append(toIndentedString(checksum))
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
