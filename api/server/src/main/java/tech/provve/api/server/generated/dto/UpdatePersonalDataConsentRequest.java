package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePersonalDataConsentRequest {

    private Boolean consentPersonalData;

    private String authToken;

    public UpdatePersonalDataConsentRequest() {

    }

    public UpdatePersonalDataConsentRequest(Boolean consentPersonalData, String authToken) {
        this.consentPersonalData = consentPersonalData;
        this.authToken = authToken;
    }


    @JsonProperty("consent_personal_data")
    public Boolean getConsentPersonalData() {
        return consentPersonalData;
    }

    public void setConsentPersonalData(Boolean consentPersonalData) {
        this.consentPersonalData = consentPersonalData;
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
        UpdatePersonalDataConsentRequest updatePersonalDataConsentRequest = (UpdatePersonalDataConsentRequest) o;
        return Objects.equals(consentPersonalData, updatePersonalDataConsentRequest.consentPersonalData) &&
                Objects.equals(authToken, updatePersonalDataConsentRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consentPersonalData, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdatePersonalDataConsentRequest {\n");

        sb.append("    consentPersonalData: ")
          .append(toIndentedString(consentPersonalData))
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
