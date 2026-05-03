package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.VoteResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Votes {

    private List<VoteResponse> votes = new ArrayList<>();
    private Cursor cursor;

    public Votes() {

    }

    public Votes(List<VoteResponse> votes, Cursor cursor) {
        this.votes = votes;
        this.cursor = cursor;
    }


    @JsonProperty("votes")
    public List<VoteResponse> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteResponse> votes) {
        this.votes = votes;
    }


    @JsonProperty("cursor")
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
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
               Objects.equals(cursor, votes.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(votes, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Votes {\n");

        sb.append("    votes: ")
          .append(toIndentedString(votes))
          .append("\n");
        sb.append("    cursor: ")
          .append(toIndentedString(cursor))
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
