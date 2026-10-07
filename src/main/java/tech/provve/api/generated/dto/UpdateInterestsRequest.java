package tech.provve.api.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateInterestsRequest {

    private List<String> interests = new ArrayList<>();
    private String authToken;

    public UpdateInterestsRequest() {

    }

    public UpdateInterestsRequest(List<String> interests, String authToken) {
        this.interests = interests;
        this.authToken = authToken;
    }


    @JsonProperty("interests")
    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
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
        UpdateInterestsRequest updateInterestsRequest = (UpdateInterestsRequest) o;
        return Objects.equals(interests, updateInterestsRequest.interests) &&
                Objects.equals(authToken, updateInterestsRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interests, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateInterestsRequest {\n");

        sb.append("    interests: ")
          .append(toIndentedString(interests))
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
