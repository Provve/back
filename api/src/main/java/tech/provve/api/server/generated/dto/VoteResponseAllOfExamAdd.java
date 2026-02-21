package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.FileUpload;

/**
 * Данные голосования на добавление экзамена (type &#x3D; exam_add)
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponseAllOfExamAdd {

    private String skillName;
    private String description;
    private FileUpload material;

    public VoteResponseAllOfExamAdd() {

    }

    public VoteResponseAllOfExamAdd(String skillName, String description, FileUpload material) {
        this.skillName = skillName;
        this.description = description;
        this.material = material;
    }


    @JsonProperty("skill_name")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }


    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        VoteResponseAllOfExamAdd voteResponseAllOfExamAdd = (VoteResponseAllOfExamAdd) o;
        return Objects.equals(skillName, voteResponseAllOfExamAdd.skillName) &&
                Objects.equals(description, voteResponseAllOfExamAdd.description) &&
                Objects.equals(material, voteResponseAllOfExamAdd.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillName, description, material);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class VoteResponseAllOfExamAdd {\n");

        sb.append("    skillName: ")
          .append(toIndentedString(skillName))
          .append("\n");
        sb.append("    description: ")
          .append(toIndentedString(description))
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
