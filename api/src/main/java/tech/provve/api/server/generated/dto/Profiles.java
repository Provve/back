package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.ProfilePublicView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profiles {

    private List<ProfilePublicView> profiles = new ArrayList<>();
    private Cursor cursor;

    public Profiles() {

    }

    public Profiles(List<ProfilePublicView> profiles, Cursor cursor) {
        this.profiles = profiles;
        this.cursor = cursor;
    }


    @JsonProperty("profiles")
    public List<ProfilePublicView> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfilePublicView> profiles) {
        this.profiles = profiles;
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
        Profiles profiles = (Profiles) o;
        return Objects.equals(profiles, profiles.profiles) &&
               Objects.equals(cursor, profiles.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profiles, cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Profiles {\n");

        sb.append("    profiles: ")
          .append(toIndentedString(profiles))
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
