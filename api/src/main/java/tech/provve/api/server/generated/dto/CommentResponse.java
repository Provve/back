package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.ProfileResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    private Integer id;
    private ProfileResponse author;
    private String content;
    private OffsetDateTime created;
    private List<CommentResponse> replies = new ArrayList<>();

    public CommentResponse() {

    }

    public CommentResponse(Integer id, ProfileResponse author, String content, OffsetDateTime created, List<CommentResponse> replies) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.created = created;
        this.replies = replies;
    }


    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    @JsonProperty("created")
    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }


    @JsonProperty("replies")
    public List<CommentResponse> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentResponse> replies) {
        this.replies = replies;
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
        return Objects.equals(id, commentResponse.id) &&
               Objects.equals(author, commentResponse.author) &&
               Objects.equals(content, commentResponse.content) &&
               Objects.equals(created, commentResponse.created) &&
               Objects.equals(replies, commentResponse.replies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, content, created, replies);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentResponse {\n");

        sb.append("    id: ")
          .append(toIndentedString(id))
          .append("\n");
        sb.append("    author: ")
          .append(toIndentedString(author))
          .append("\n");
        sb.append("    content: ")
          .append(toIndentedString(content))
          .append("\n");
        sb.append("    created: ")
          .append(toIndentedString(created))
          .append("\n");
        sb.append("    replies: ")
          .append(toIndentedString(replies))
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
