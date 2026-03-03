package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.FileUpload;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamAddVoteResponse {

    private String skillName;
    private String description;
    private FileUpload publicArchiveUrl;
    private FileUpload privateArchiveUrl;

    public ExamAddVoteResponse() {

    }

    public ExamAddVoteResponse(String skillName, String description, FileUpload publicArchiveUrl, FileUpload privateArchiveUrl) {
        this.skillName = skillName;
        this.description = description;
        this.publicArchiveUrl = publicArchiveUrl;
        this.privateArchiveUrl = privateArchiveUrl;
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


    @JsonProperty("public_archive_url")
    public FileUpload getPublicArchiveUrl() {
        return publicArchiveUrl;
    }

    public void setPublicArchiveUrl(FileUpload publicArchiveUrl) {
        this.publicArchiveUrl = publicArchiveUrl;
    }


    @JsonProperty("private_archive_url")
    public FileUpload getPrivateArchiveUrl() {
        return privateArchiveUrl;
    }

    public void setPrivateArchiveUrl(FileUpload privateArchiveUrl) {
        this.privateArchiveUrl = privateArchiveUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamAddVoteResponse examAddVoteResponse = (ExamAddVoteResponse) o;
        return Objects.equals(skillName, examAddVoteResponse.skillName) &&
                Objects.equals(description, examAddVoteResponse.description) &&
                Objects.equals(publicArchiveUrl, examAddVoteResponse.publicArchiveUrl) &&
                Objects.equals(privateArchiveUrl, examAddVoteResponse.privateArchiveUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillName, description, publicArchiveUrl, privateArchiveUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExamAddVoteResponse {\n");

        sb.append("    skillName: ")
          .append(toIndentedString(skillName))
          .append("\n");
        sb.append("    description: ")
          .append(toIndentedString(description))
          .append("\n");
        sb.append("    publicArchiveUrl: ")
          .append(toIndentedString(publicArchiveUrl))
          .append("\n");
        sb.append("    privateArchiveUrl: ")
          .append(toIndentedString(privateArchiveUrl))
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
