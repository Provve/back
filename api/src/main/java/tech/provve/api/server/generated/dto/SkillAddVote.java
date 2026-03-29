package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillAddVote {

    private String name;
    private String arguments;
    private List<String> tags = new ArrayList<>();
    private String authToken;

    public SkillAddVote() {

    }

    public SkillAddVote(String name, String arguments, List<String> tags, String authToken) {
        this.name = name;
        this.arguments = arguments;
        this.tags = tags;
        this.authToken = authToken;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkillAddVote skillAddVote = (SkillAddVote) o;
        return Objects.equals(name, skillAddVote.name) &&
               Objects.equals(arguments, skillAddVote.arguments) &&
               Objects.equals(tags, skillAddVote.tags) &&
               Objects.equals(authToken, skillAddVote.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, arguments, tags, authToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SkillAddVote {\n");

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
