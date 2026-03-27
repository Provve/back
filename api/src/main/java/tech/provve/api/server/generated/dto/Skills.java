package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Skills {

    private List<SkillResponse> skills = new ArrayList<>();
    private Pagination pagination;

    public Skills() {

    }

    public Skills(List<SkillResponse> skills, Pagination pagination) {
        this.skills = skills;
        this.pagination = pagination;
    }


    @JsonProperty("skills")
    public List<SkillResponse> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillResponse> skills) {
        this.skills = skills;
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
        Skills skills = (Skills) o;
        return Objects.equals(skills, skills.skills) &&
               Objects.equals(pagination, skills.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills, pagination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Skills {\n");

        sb.append("    skills: ")
          .append(toIndentedString(skills))
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
