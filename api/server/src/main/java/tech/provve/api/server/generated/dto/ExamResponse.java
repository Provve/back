package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamResponse {

    private String name;

    private String desc;

    private java.util.UUID session;

    public ExamResponse() {

    }

    public ExamResponse(String name, String desc, java.util.UUID session) {
        this.name = name;
        this.desc = desc;
        this.session = session;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @JsonProperty("session")
    public java.util.UUID getSession() {
        return session;
    }

    public void setSession(java.util.UUID session) {
        this.session = session;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamResponse examResponse = (ExamResponse) o;
        return Objects.equals(name, examResponse.name) &&
                Objects.equals(desc, examResponse.desc) &&
                Objects.equals(session, examResponse.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, session);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExamResponse {\n");

        sb.append("    name: ")
          .append(toIndentedString(name))
          .append("\n");
        sb.append("    desc: ")
          .append(toIndentedString(desc))
          .append("\n");
        sb.append("    session: ")
          .append(toIndentedString(session))
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
