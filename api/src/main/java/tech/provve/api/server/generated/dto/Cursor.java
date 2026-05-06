package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Указатель на последний элемент предыдущей страницы
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cursor {

    private String previous;

    public Cursor() {

    }

    public Cursor(String previous) {
        this.previous = previous;
    }


    @JsonProperty("previous")
    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cursor cursor = (Cursor) o;
        return Objects.equals(previous, cursor.previous);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previous);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Cursor {\n");

        sb.append("    previous: ")
          .append(toIndentedString(previous))
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
