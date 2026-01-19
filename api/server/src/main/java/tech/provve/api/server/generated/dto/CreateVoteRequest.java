package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.vertx.ext.web.FileUpload;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVoteRequest {


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

    private String name;

    private java.util.UUID id;

    private java.util.UUID skillId;

    private String desc;

    private FileUpload material;

    public CreateVoteRequest() {

    }

    public CreateVoteRequest(ActionEnum action, String arguments, String name, java.util.UUID id, java.util.UUID skillId, String desc, FileUpload material) {
        this.action = action;
        this.arguments = arguments;
        this.name = name;
        this.id = id;
        this.skillId = skillId;
        this.desc = desc;
        this.material = material;
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


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("id")
    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }


    @JsonProperty("skill_id")
    public java.util.UUID getSkillId() {
        return skillId;
    }

    public void setSkillId(java.util.UUID skillId) {
        this.skillId = skillId;
    }


    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @JsonProperty("material")
    public FileUpload getMaterial() {
        return material;
    }

    public void setMaterial(FileUpload material) {
        this.material = material;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateVoteRequest createVoteRequest = (CreateVoteRequest) o;
        return Objects.equals(action, createVoteRequest.action) &&
                Objects.equals(arguments, createVoteRequest.arguments) &&
                Objects.equals(name, createVoteRequest.name) &&
                Objects.equals(id, createVoteRequest.id) &&
                Objects.equals(skillId, createVoteRequest.skillId) &&
                Objects.equals(desc, createVoteRequest.desc) &&
                Objects.equals(material, createVoteRequest.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, arguments, name, id, skillId, desc, material);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateVoteRequest {\n");

        sb.append("    action: ")
          .append(toIndentedString(action))
          .append("\n");
        sb.append("    arguments: ")
          .append(toIndentedString(arguments))
          .append("\n");
        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    id: ")
          .append(toIndentedString(id))
          .append("\n");
        sb.append("    skillId: ")
          .append(toIndentedString(skillId))
          .append("\n");
        sb.append("    desc: ")
          .append(toIndentedString(desc))
          .append("\n");
        sb.append("    material: ")
          .append(toIndentedString(material))
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
