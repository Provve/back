package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notifications {

    private List<Notification> notifications = new ArrayList<>();
    private Cursor cursor;

    public Notifications() {

    }

    public Notifications(List<Notification> notifications, Cursor cursor) {
        this.notifications = notifications;
        this.cursor = cursor;
    }


    @JsonProperty("notifications")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }


    @JsonProperty("cursor")
    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
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
               Objects.equals(cursor, notifications.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notifications, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Notifications {\n");

        sb.append("    notifications: ")
          .append(toIndentedString(notifications))
          .append("\n");
        sb.append("    cursor: ")
          .append(toIndentedString(cursor))
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
