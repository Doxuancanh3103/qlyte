package vt.qlkdtt.yte.web.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrganizationUpdateRequest {

    @NotBlank
    @JsonProperty(value ="name", required = true)
    String name;

    @JsonProperty(value ="logo")
    String logo;

    @JsonProperty(value ="description")
    String description;

    @NotBlank
    @JsonProperty(value ="status", required = true)
    String status;

    //
    @JsonProperty(value ="parent_id")
    Long parentId;
}
