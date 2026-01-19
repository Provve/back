package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CastVoteRequest {


    public enum ReactionEnum {
        _1("+1"),
        _12("-1");

        private String value;

        ReactionEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    private ReactionEnum reaction;

    public CastVoteRequest() {

    }

    public CastVoteRequest(ReactionEnum reaction) {
        this.reaction = reaction;
    }


    @JsonProperty("reaction")
    public ReactionEnum getReaction() {
        return reaction;
    }

    public void setReaction(ReactionEnum reaction) {
        this.reaction = reaction;
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
        return Objects.equals(reaction, castVoteRequest.reaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reaction);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CastVoteRequest {\n");

        sb.append("    reaction: ")
          .append(toIndentedString(reaction))
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
