package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Всеохватывающее представление голосования
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponse {

    private String name;
    private String arguments;
    private List<String> tags = new ArrayList<>();
    private String authToken;


    public enum TypeEnum {
        SKILL_ADD("skill_add"),
        SKILL_DEL("skill_del"),
        EXAM_ADD("exam_add");

        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    private TypeEnum type;
    private VoteResponseAllOfReactions reactions;
    private OffsetDateTime deadline;
    private ExamAddVoteResponse examAdd;

    public VoteResponse() {

    }

    public VoteResponse(String name,
                        String arguments,
                        List<String> tags,
                        String authToken,
                        TypeEnum type,
                        VoteResponseAllOfReactions reactions,
                        OffsetDateTime deadline,
                        ExamAddVoteResponse examAdd) {
        this.name = name;
        this.arguments = arguments;
        this.tags = tags;
        this.authToken = authToken;
        this.type = type;
        this.reactions = reactions;
        this.deadline = deadline;
        this.examAdd = examAdd;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("arguments")
    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }


    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    @JsonProperty("type")
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }


    @JsonProperty("reactions")
    public VoteResponseAllOfReactions getReactions() {
        return reactions;
    }

    public void setReactions(VoteResponseAllOfReactions reactions) {
        this.reactions = reactions;
    }


    @JsonProperty("deadline")
    public OffsetDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OffsetDateTime deadline) {
        this.deadline = deadline;
    }


    @JsonProperty("exam_add")
    public ExamAddVoteResponse getExamAdd() {
        return examAdd;
    }

    public void setExamAdd(ExamAddVoteResponse examAdd) {
        this.examAdd = examAdd;
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
        return Objects.equals(name, voteResponse.name) &&
                Objects.equals(arguments, voteResponse.arguments) &&
                Objects.equals(tags, voteResponse.tags) &&
                Objects.equals(authToken, voteResponse.authToken) &&
                Objects.equals(type, voteResponse.type) &&
                Objects.equals(reactions, voteResponse.reactions) &&
                Objects.equals(deadline, voteResponse.deadline) &&
                Objects.equals(examAdd, voteResponse.examAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments, tags, authToken, type, reactions, deadline, examAdd);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VoteResponse {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    arguments: ")
          .append(toIndentedString(arguments))
          .append("\n");
        sb.append("    tags: ")
          .append(toIndentedString(tags))
          .append("\n");
        sb.append("    authToken: ")
          .append(toIndentedString(authToken))
          .append("\n");
        sb.append("    type: ")
          .append(toIndentedString(type))
          .append("\n");
        sb.append("    reactions: ")
          .append(toIndentedString(reactions))
          .append("\n");
        sb.append("    deadline: ")
          .append(toIndentedString(deadline))
          .append("\n");
        sb.append("    examAdd: ")
          .append(toIndentedString(examAdd))
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
