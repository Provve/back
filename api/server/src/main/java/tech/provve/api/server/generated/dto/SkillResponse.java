package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillResponse {

    private String name;

    private java.util.UUID voteId;

    public SkillResponse() {

    }

    public SkillResponse(String name, java.util.UUID voteId) {
        this.name = name;
        this.voteId = voteId;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("vote_id")
    public java.util.UUID getVoteId() {
        return voteId;
    }

    public void setVoteId(java.util.UUID voteId) {
        this.voteId = voteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkillResponse skillResponse = (SkillResponse) o;
        return Objects.equals(name, skillResponse.name) &&
                Objects.equals(voteId, skillResponse.voteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voteId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkillResponse {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    voteId: ")
          .append(toIndentedString(voteId))
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
