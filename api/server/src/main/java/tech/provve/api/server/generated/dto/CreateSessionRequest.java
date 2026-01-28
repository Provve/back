package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionRequest {

    private java.util.UUID examId;

    private Integer cipher;

    public CreateSessionRequest() {

    }

    public CreateSessionRequest(java.util.UUID examId, Integer cipher) {
        this.examId = examId;
        this.cipher = cipher;
    }


    @JsonProperty("exam_id")
    public java.util.UUID getExamId() {
        return examId;
    }

    public void setExamId(java.util.UUID examId) {
        this.examId = examId;
    }


    @JsonProperty("cipher")
    public Integer getCipher() {
        return cipher;
    }

    public void setCipher(Integer cipher) {
        this.cipher = cipher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateSessionRequest createSessionRequest = (CreateSessionRequest) o;
        return Objects.equals(examId, createSessionRequest.examId) &&
                Objects.equals(cipher, createSessionRequest.cipher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, cipher);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateSessionRequest {\n");

        sb.append("    examId: ")
          .append(toIndentedString(examId))
          .append("\n");
        sb.append("    cipher: ")
          .append(toIndentedString(cipher))
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
