package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCommentsRequest {

    private String voteName;
    private Pagination pagination;

    public ListCommentsRequest() {

    }

    public ListCommentsRequest(String voteName, Pagination pagination) {
        this.voteName = voteName;
        this.pagination = pagination;
    }


    @JsonProperty("vote_name")
    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }


    @JsonProperty("pagination")
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListCommentsRequest listCommentsRequest = (ListCommentsRequest) o;
        return Objects.equals(voteName, listCommentsRequest.voteName) &&
               Objects.equals(pagination, listCommentsRequest.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteName, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListCommentsRequest {\n");

        sb.append("    voteName: ")
          .append(toIndentedString(voteName))
          .append("\n");
        sb.append("    pagination: ")
          .append(toIndentedString(pagination))
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
