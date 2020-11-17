package vt.qlkdtt.yte.service.sdo;


import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrganizationSdo implements Serializable {

    long id;
    //
    String name;
    String logo;
    String description;
    String status;

    //
    Long parentId;

    //  audit meta
    String createdBy;
    Date createdTime;
    String lastModifiedBy;
    Date lastModifiedTime;
}
