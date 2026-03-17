package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CastVoteRequest {

    private Boolean positiveReaction;
    private String authToken;

    public CastVoteRequest() {

    }

    public CastVoteRequest(Boolean positiveReaction, String authToken) {
        this.positiveReaction = positiveReaction;
        this.authToken = authToken;
    }


    @JsonProperty("positive_reaction")
    public Boolean getPositiveReaction() {
        return positiveReaction;
    }

    public void setPositiveReaction(Boolean positiveReaction) {
        this.positiveReaction = positiveReaction;
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
        CastVoteRequest castVoteRequest = (CastVoteRequest) o;
        return Objects.equals(positiveReaction, castVoteRequest.positiveReaction) &&
                Objects.equals(authToken, castVoteRequest.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positiveReaction, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CastVoteRequest {\n");

        sb.append("    positiveReaction: ")
          .append(toIndentedString(positiveReaction))
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
