package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Голоса
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponseAllOfReactions {

    private Integer positive;
    private Integer negative;

    public VoteResponseAllOfReactions() {

    }

    public VoteResponseAllOfReactions(Integer positive, Integer negative) {
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
        VoteResponseAllOfReactions voteResponseAllOfReactions = (VoteResponseAllOfReactions) o;
        return Objects.equals(positive, voteResponseAllOfReactions.positive) &&
                Objects.equals(negative, voteResponseAllOfReactions.negative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positive, negative);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VoteResponseAllOfReactions {\n");

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
