package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.Pagination;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListNotifications {

    private String authToken;
    private Pagination pagination;

    public ListNotifications() {

    }

    public ListNotifications(String authToken, Pagination pagination) {
        this.authToken = authToken;
        this.pagination = pagination;
    }


    @JsonProperty("auth_token")
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
        ListNotifications listNotifications = (ListNotifications) o;
        return Objects.equals(authToken, listNotifications.authToken) &&
                Objects.equals(pagination, listNotifications.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListNotifications {\n");

        sb.append("    authToken: ")
          .append(toIndentedString(authToken))
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
