package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.Contacts;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateContactsRequest {

    private Contacts contacts;
    private String authToken;

    public UpdateContactsRequest() {

    }

    public UpdateContactsRequest(Contacts contacts, String authToken) {
        this.contacts = contacts;
        this.authToken = authToken;
    }


    @JsonProperty("contacts")
    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
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
        UpdateContactsRequest updateContactsRequest = (UpdateContactsRequest) o;
        return Objects.equals(contacts, updateContactsRequest.contacts) &&
                Objects.equals(authToken, updateContactsRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contacts, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateContactsRequest {\n");

        sb.append("    contacts: ")
          .append(toIndentedString(contacts))
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
