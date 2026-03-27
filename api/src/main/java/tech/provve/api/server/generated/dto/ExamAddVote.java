package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.FileUpload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamAddVote {
  
  private String name;
  private String arguments;
  private List<String> tags = new ArrayList<>();
  private String authToken;
  private String skillName;
  private String description;
  private FileUpload publicArchive;
  private FileUpload privateArchive;

  public ExamAddVote() {

  }

  public ExamAddVote(String name,
                     String arguments,
                     List<String> tags,
                     String authToken,
                     String skillName,
                     String description,
                     FileUpload publicArchive,
                     FileUpload privateArchive) {
    this.name = name;
    this.arguments = arguments;
    this.tags = tags;
    this.authToken = authToken;
    this.skillName = skillName;
    this.description = description;
    this.publicArchive = publicArchive;
    this.privateArchive = privateArchive;
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


  @JsonProperty("public_archive")
  public FileUpload getPublicArchive() {
    return publicArchive;
  }
  public void setPublicArchive(FileUpload publicArchive) {
    this.publicArchive = publicArchive;
  }


  @JsonProperty("private_archive")
  public FileUpload getPrivateArchive() {
    return privateArchive;
  }
  public void setPrivateArchive(FileUpload privateArchive) {
    this.privateArchive = privateArchive;
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
    return Objects.equals(name, examAddVote.name) &&
           Objects.equals(arguments, examAddVote.arguments) &&
           Objects.equals(tags, examAddVote.tags) &&
           Objects.equals(authToken, examAddVote.authToken) &&
           Objects.equals(skillName, examAddVote.skillName) &&
           Objects.equals(description, examAddVote.description) &&
           Objects.equals(publicArchive, examAddVote.publicArchive) &&
           Objects.equals(privateArchive, examAddVote.privateArchive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, arguments, tags, authToken, skillName, description, publicArchive, privateArchive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExamAddVote {\n");

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
    sb.append("    skillName: ")
      .append(toIndentedString(skillName))
      .append("\n");
    sb.append("    description: ")
      .append(toIndentedString(description))
      .append("\n");
    sb.append("    publicArchive: ")
      .append(toIndentedString(publicArchive))
      .append("\n");
    sb.append("    privateArchive: ")
      .append(toIndentedString(privateArchive))
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
