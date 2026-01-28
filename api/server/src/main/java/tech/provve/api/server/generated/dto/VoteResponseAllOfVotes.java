package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Голоса
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponseAllOfVotes {

    private Integer positive;

    private Integer negative;

    public VoteResponseAllOfVotes() {

    }

    public VoteResponseAllOfVotes(Integer positive, Integer negative) {
        this.positive = positive;
        this.negative = negative;
    }


    @JsonProperty("positive")
    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }


    @JsonProperty("negative")
    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoteResponseAllOfVotes voteResponseAllOfVotes = (VoteResponseAllOfVotes) o;
        return Objects.equals(positive, voteResponseAllOfVotes.positive) &&
                Objects.equals(negative, voteResponseAllOfVotes.negative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positive, negative);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VoteResponseAllOfVotes {\n");

        sb.append("    positive: ")
          .append(toIndentedString(positive))
          .append("\n");
        sb.append("    negative: ")
          .append(toIndentedString(negative))
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
