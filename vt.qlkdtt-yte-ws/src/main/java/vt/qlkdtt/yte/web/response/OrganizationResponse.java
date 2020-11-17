package vt.qlkdtt.yte.web.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@SuppressWarnings("serial")
public class OrganizationResponse implements Serializable{


    @JsonProperty(value ="id")
    long id;

    @JsonProperty(value ="name")
    String name;

    @JsonProperty(value ="logo")
    String logo;

    @JsonProperty(value ="description")
    String description;

    @JsonProperty(value ="status")
    String status;

    //
    @JsonProperty(value ="parent_id")
    Long parentId;

    //  audit meta
    @JsonProperty(value ="created_by")
    String createdBy;

    @JsonProperty(value ="created_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    Date createdTime;

    @JsonProperty(value ="last_modified_by")
    String lastModifiedBy;

    @JsonProperty(value = "last_modified_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    Date lastModifiedTime;
}
