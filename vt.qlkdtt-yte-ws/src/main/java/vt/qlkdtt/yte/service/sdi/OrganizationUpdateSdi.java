package vt.qlkdtt.yte.service.sdi;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrganizationUpdateSdi{

    long id;
    //
    String name;
    String logo;
    String description;
    String status;
    //
    Long parentId;

}
