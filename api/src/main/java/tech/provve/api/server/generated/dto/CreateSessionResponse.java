package tech.provve.api.server.generated.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionResponse {

    private Boolean _continue;
    private URI redirect;

    public CreateSessionResponse() {

    }

    public CreateSessionResponse(Boolean _continue, URI redirect) {
        this._continue = _continue;
        this.redirect = redirect;
    }


    @JsonProperty("continue")
    public Boolean getContinue() {
        return _continue;
    }

    public void setContinue(Boolean _continue) {
        this._continue = _continue;
    }


    @JsonProperty("redirect")
    public URI getRedirect() {
        return redirect;
    }

    public void setRedirect(URI redirect) {
        this.redirect = redirect;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateSessionResponse createSessionResponse = (CreateSessionResponse) o;
        return Objects.equals(_continue, createSessionResponse._continue) &&
                Objects.equals(redirect, createSessionResponse.redirect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_continue, redirect);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateSessionResponse {\n");

        sb.append("    _continue: ")
          .append(toIndentedString(_continue))
          .append("\n");
        sb.append("    redirect: ")
          .append(toIndentedString(redirect))
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
