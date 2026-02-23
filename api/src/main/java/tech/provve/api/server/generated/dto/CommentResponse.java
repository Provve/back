package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    private ProfileResponse author;
    private String content;
    private Integer id;

    public CommentResponse() {

    }

    public CommentResponse(ProfileResponse author, String content, Integer id) {
        this.author = author;
        this.content = content;
        this.id = id;
    }


    @JsonProperty("author")
    public ProfileResponse getAuthor() {
        return author;
    }

    public void setAuthor(ProfileResponse author) {
        this.author = author;
    }


    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        CommentResponse commentResponse = (CommentResponse) o;
        return Objects.equals(author, commentResponse.author) &&
                Objects.equals(content, commentResponse.content) &&
                Objects.equals(id, commentResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentResponse {\n");

        sb.append("    author: ")
          .append(toIndentedString(author))
          .append("\n");
        sb.append("    content: ")
          .append(toIndentedString(content))
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
