package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;

import tech.provve.api.server.generated.dto.VoteResponseAllOfVotes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponse {


    public enum ActionEnum {
        DELETE_SKILL("delete_skill"),
        ADD_SKILL("add_skill"),
        ADD_EXAM("add_exam");

        private String value;

        ActionEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    private ActionEnum action;

    private String arguments;

    private VoteResponseAllOfVotes votes;

    private OffsetDateTime expireAt;

    public VoteResponse() {

    }

    public VoteResponse(ActionEnum action, String arguments, VoteResponseAllOfVotes votes, OffsetDateTime expireAt) {
        this.action = action;
        this.arguments = arguments;
        this.votes = votes;
        this.expireAt = expireAt;
    }


    @JsonProperty("action")
    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }


    @JsonProperty("arguments")
    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }


    @JsonProperty("votes")
    public VoteResponseAllOfVotes getVotes() {
        return votes;
    }

    public void setVotes(VoteResponseAllOfVotes votes) {
        this.votes = votes;
    }


    @JsonProperty("expire_at")
    public OffsetDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(OffsetDateTime expireAt) {
        this.expireAt = expireAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoteResponse voteResponse = (VoteResponse) o;
        return Objects.equals(action, voteResponse.action) &&
                Objects.equals(arguments, voteResponse.arguments) &&
                Objects.equals(votes, voteResponse.votes) &&
                Objects.equals(expireAt, voteResponse.expireAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, arguments, votes, expireAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VoteResponse {\n");

        sb.append("    action: ")
          .append(toIndentedString(action))
          .append("\n");
        sb.append("    arguments: ")
          .append(toIndentedString(arguments))
          .append("\n");
        sb.append("    votes: ")
          .append(toIndentedString(votes))
          .append("\n");
        sb.append("    expireAt: ")
          .append(toIndentedString(expireAt))
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
