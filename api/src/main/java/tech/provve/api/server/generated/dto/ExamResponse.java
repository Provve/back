package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamResponse {

    private String name;
    private String description;
    private String publicArchiveUrl;

    public ExamResponse() {

    }

    public ExamResponse(String name, String description, String publicArchiveUrl) {
        this.name = name;
        this.description = description;
        this.publicArchiveUrl = publicArchiveUrl;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamResponse examResponse = (ExamResponse) o;
        return Objects.equals(name, examResponse.name) &&
               Objects.equals(description, examResponse.description) &&
               Objects.equals(publicArchiveUrl, examResponse.publicArchiveUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, publicArchiveUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExamResponse {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    description: ")
          .append(toIndentedString(description))
          .append("\n");
        sb.append("    publicArchiveUrl: ")
          .append(toIndentedString(publicArchiveUrl))
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
