package tech.provve.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillResponse {

    private String name;
    private String description;
    private String publicArchiveUrl;
    private List<String> tags = new ArrayList<>();

    public SkillResponse() {

    }

    public SkillResponse(String name, String description, String publicArchiveUrl, List<String> tags) {
        this.name = name;
        this.description = description;
        this.publicArchiveUrl = publicArchiveUrl;
        this.tags = tags;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @JsonProperty("public_archive_url")
    public String getPublicArchiveUrl() {
        return publicArchiveUrl;
    }

    public void setPublicArchiveUrl(String publicArchiveUrl) {
        this.publicArchiveUrl = publicArchiveUrl;
    }


    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
                Objects.equals(description, skillResponse.description) &&
                Objects.equals(publicArchiveUrl, skillResponse.publicArchiveUrl) &&
                Objects.equals(tags, skillResponse.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, publicArchiveUrl, tags);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkillResponse {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    description: ")
          .append(toIndentedString(description))
          .append("\n");
        sb.append("    publicArchiveUrl: ")
          .append(toIndentedString(publicArchiveUrl))
          .append("\n");
        sb.append("    tags: ")
          .append(toIndentedString(tags))
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
