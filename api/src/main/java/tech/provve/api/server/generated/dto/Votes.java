package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.VoteResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Votes {

    private List<VoteResponse> votes = new ArrayList<>();
    private Pagination pagination;

    public Votes() {

    }

    public Votes(List<VoteResponse> votes, Pagination pagination) {
        this.votes = votes;
        this.pagination = pagination;
    }


    @JsonProperty("votes")
    public List<VoteResponse> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteResponse> votes) {
        this.votes = votes;
    }


    @JsonProperty("pagination")
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Votes votes = (Votes) o;
        return Objects.equals(votes, votes.votes) &&
               Objects.equals(pagination, votes.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(votes, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Votes {\n");

        sb.append("    votes: ")
          .append(toIndentedString(votes))
          .append("\n");
        sb.append("    pagination: ")
          .append(toIndentedString(pagination))
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
