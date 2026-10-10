package tech.provve.api.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillAddVoteResponse {

    private String description;
    private String publicArchiveUrl;
    private String privateArchiveUrl;

    public SkillAddVoteResponse() {

    }

    public SkillAddVoteResponse(String description, String publicArchiveUrl, String privateArchiveUrl) {
        this.description = description;
        this.publicArchiveUrl = publicArchiveUrl;
        this.privateArchiveUrl = privateArchiveUrl;
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


    @JsonProperty("private_archive_url")
    public String getPrivateArchiveUrl() {
        return privateArchiveUrl;
    }

    public void setPrivateArchiveUrl(String privateArchiveUrl) {
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
        SkillAddVoteResponse skillAddVoteResponse = (SkillAddVoteResponse) o;
        return Objects.equals(description, skillAddVoteResponse.description) &&
                Objects.equals(publicArchiveUrl, skillAddVoteResponse.publicArchiveUrl) &&
                Objects.equals(privateArchiveUrl, skillAddVoteResponse.privateArchiveUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, publicArchiveUrl, privateArchiveUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkillAddVoteResponse {\n");

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
