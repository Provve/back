package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CastVoteRequest {

    private String name;
    private Boolean positiveReaction;

    public CastVoteRequest() {

    }

    public CastVoteRequest(String name, Boolean positiveReaction) {
        this.name = name;
        this.positiveReaction = positiveReaction;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("positive_reaction")
    public Boolean getPositiveReaction() {
        return positiveReaction;
    }

    public void setPositiveReaction(Boolean positiveReaction) {
        this.positiveReaction = positiveReaction;
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
        return Objects.equals(name, castVoteRequest.name) &&
                Objects.equals(positiveReaction, castVoteRequest.positiveReaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, positiveReaction);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CastVoteRequest {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    positiveReaction: ")
          .append(toIndentedString(positiveReaction))
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
