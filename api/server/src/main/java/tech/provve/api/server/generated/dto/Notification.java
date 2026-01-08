package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Уведомление о событии в системе
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {


    private LevelEnum level;

    private String message;

    private OffsetDateTime createdAt;

    public Notification() {

    }

    public Notification(LevelEnum level, String message, OffsetDateTime createdAt) {
        this.level = level;
        this.message = message;
        this.createdAt = createdAt;
    }

    @JsonProperty("level")
    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        return Objects.equals(level, notification.level) &&
                Objects.equals(message, notification.message) &&
                Objects.equals(createdAt, notification.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, message, createdAt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Notification {\n");

        sb.append("    level: ")
          .append(toIndentedString(level))
          .append("\n");
        sb.append("    message: ")
          .append(toIndentedString(message))
          .append("\n");
        sb.append("    createdAt: ")
          .append(toIndentedString(createdAt))
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

    public enum LevelEnum {
        ERROR("error"),
        WARNING("warning"),
        INFO("info");

        private String value;

        LevelEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
