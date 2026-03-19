package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notifications {

    private List<Notification> notifications = new ArrayList<>();
    private Pagination pagination;

    public Notifications() {

    }

    public Notifications(List<Notification> notifications, Pagination pagination) {
        this.notifications = notifications;
        this.pagination = pagination;
    }


    @JsonProperty("notifications")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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
        Notifications notifications = (Notifications) o;
        return Objects.equals(notifications, notifications.notifications) &&
                Objects.equals(pagination, notifications.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notifications, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Notifications {\n");

        sb.append("    notifications: ")
          .append(toIndentedString(notifications))
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
