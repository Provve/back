package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.Notification;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionCreatedNotification {

    private Notification notification;

    private String hash;

    public SessionCreatedNotification() {

    }

    public SessionCreatedNotification(Notification notification, String hash) {
        this.notification = notification;
        this.hash = hash;
    }


    @JsonProperty("notification")
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionCreatedNotification sessionCreatedNotification = (SessionCreatedNotification) o;
        return Objects.equals(notification, sessionCreatedNotification.notification) &&
                Objects.equals(hash, sessionCreatedNotification.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notification, hash);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SessionCreatedNotification {\n");

        sb.append("    notification: ")
          .append(toIndentedString(notification))
          .append("\n");
        sb.append("    hash: ")
          .append(toIndentedString(hash))
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
