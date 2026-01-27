package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.vertx.ext.web.FileUpload;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamAddVote {


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

    private java.util.UUID skillId;

    private String name;

    private String desc;

    private FileUpload material;

    public ExamAddVote() {

    }

    public ExamAddVote(ActionEnum action, String arguments, java.util.UUID skillId, String name, String desc, FileUpload material) {
        this.action = action;
        this.arguments = arguments;
        this.skillId = skillId;
        this.name = name;
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


    @JsonProperty("skill_id")
    public java.util.UUID getSkillId() {
        return skillId;
    }

    public void setSkillId(java.util.UUID skillId) {
        this.skillId = skillId;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        ExamAddVote examAddVote = (ExamAddVote) o;
        return Objects.equals(action, examAddVote.action) &&
                Objects.equals(arguments, examAddVote.arguments) &&
                Objects.equals(skillId, examAddVote.skillId) &&
                Objects.equals(name, examAddVote.name) &&
                Objects.equals(desc, examAddVote.desc) &&
                Objects.equals(material, examAddVote.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, arguments, skillId, name, desc, material);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExamAddVote {\n");

        sb.append("    action: ")
          .append(toIndentedString(action))
          .append("\n");
        sb.append("    arguments: ")
          .append(toIndentedString(arguments))
          .append("\n");
        sb.append("    skillId: ")
          .append(toIndentedString(skillId))
          .append("\n");
        sb.append("    name: ")
          .append(toIndentedString(name))
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
