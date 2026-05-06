package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.SkillResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Skills {

    private List<SkillResponse> skills = new ArrayList<>();
    private Cursor cursor;

    public Skills() {

    }

    public Skills(List<SkillResponse> skills, Cursor cursor) {
        this.skills = skills;
        this.cursor = cursor;
    }


    @JsonProperty("skills")
    public List<SkillResponse> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillResponse> skills) {
        this.skills = skills;
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
        Skills skills = (Skills) o;
        return Objects.equals(skills, skills.skills) &&
               Objects.equals(cursor, skills.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Skills {\n");

        sb.append("    skills: ")
          .append(toIndentedString(skills))
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
