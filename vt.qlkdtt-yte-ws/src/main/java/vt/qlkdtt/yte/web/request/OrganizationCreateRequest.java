package vt.qlkdtt.yte.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrganizationCreateRequest {


    @NotBlank
    @JsonProperty(value ="name", required = true)
    String name;

    @JsonProperty(value ="logo")
    String logo;

    @JsonProperty(value ="description")
    String description;

    @NotNull
    @JsonProperty(value ="status")
    String status;

    //
    @JsonProperty(value ="parent_id")
    Long parentId;
}
