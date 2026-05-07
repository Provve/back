package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.ext.web.FileUpload;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitExamSolutionRequest {

    private FileUpload solution;
    private String trustToken;

    public SubmitExamSolutionRequest() {

    }

    public SubmitExamSolutionRequest(FileUpload solution, String trustToken) {
        this.solution = solution;
        this.trustToken = trustToken;
    }


    @JsonProperty("solution")
    public FileUpload getSolution() {
        return solution;
    }

    public void setSolution(FileUpload solution) {
        this.solution = solution;
    }


    @JsonProperty("trust_token")
    public String getTrustToken() {
        return trustToken;
    }

    public void setTrustToken(String trustToken) {
        this.trustToken = trustToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubmitExamSolutionRequest submitExamSolutionRequest = (SubmitExamSolutionRequest) o;
        return Objects.equals(solution, submitExamSolutionRequest.solution) &&
               Objects.equals(trustToken, submitExamSolutionRequest.trustToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solution, trustToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SubmitExamSolutionRequest {\n");

        sb.append("    solution: ")
          .append(toIndentedString(solution))
          .append("\n");
        sb.append("    trustToken: ")
          .append(toIndentedString(trustToken))
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
