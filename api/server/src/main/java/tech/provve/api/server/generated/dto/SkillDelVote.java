package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillDelVote {


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

    private java.util.UUID id;

    public SkillDelVote() {

    }

    public SkillDelVote(ActionEnum action, String arguments, java.util.UUID id) {
        this.action = action;
        this.arguments = arguments;
        this.id = id;
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


    @JsonProperty("id")
    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkillDelVote skillDelVote = (SkillDelVote) o;
        return Objects.equals(action, skillDelVote.action) &&
                Objects.equals(arguments, skillDelVote.arguments) &&
                Objects.equals(id, skillDelVote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, arguments, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkillDelVote {\n");

        sb.append("    action: ")
          .append(toIndentedString(action))
          .append("\n");
        sb.append("    arguments: ")
          .append(toIndentedString(arguments))
          .append("\n");
        sb.append("    id: ")
          .append(toIndentedString(id))
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
