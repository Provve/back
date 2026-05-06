package tech.provve.api.server.generated.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionRequest {
  
  private Pagination pagination;
  private Filter filter;

    public CollectionRequest() {

  }

    public CollectionRequest(Pagination pagination, Filter filter) {
    this.pagination = pagination;
    this.filter = filter;
  }


    @JsonProperty("pagination")
  public Pagination getPagination() {
    return pagination;
  }
  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }


    @JsonProperty("filter")
  public Filter getFilter() {
    return filter;
  }
  public void setFilter(Filter filter) {
    this.filter = filter;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionRequest collectionRequest = (CollectionRequest) o;
    return Objects.equals(pagination, collectionRequest.pagination) &&
           Objects.equals(filter, collectionRequest.filter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, filter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionRequest {\n");

      sb.append("    pagination: ")
        .append(toIndentedString(pagination))
        .append("\n");
      sb.append("    filter: ")
        .append(toIndentedString(filter))
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
