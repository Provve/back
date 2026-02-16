package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contacts {

    private List<String> urLs = new ArrayList<>();

    public Contacts() {

    }

    public Contacts(List<String> urLs) {
        this.urLs = urLs;
    }


    @JsonProperty("URLs")
    public List<String> getUrLs() {
        return urLs;
    }

    public void setUrLs(List<String> urLs) {
        this.urLs = urLs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contacts contacts = (Contacts) o;
        return Objects.equals(urLs, contacts.urLs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urLs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Contacts {\n");

        sb.append("    urLs: ")
          .append(toIndentedString(urLs))
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
