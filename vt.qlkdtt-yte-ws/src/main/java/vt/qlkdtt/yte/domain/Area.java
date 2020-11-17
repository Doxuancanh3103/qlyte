package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "AREA")
public class Area {
    @Id
    @Column(name = "area_code")
    String areaCode;
    @Column(name = "name")
    String name;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "parent_code")
    String parentCode;
    @Column(name = "province")
    String province;
    @Column(name = "district")
    String district;
    @Column(name = "precinct")
    String precinct;
    @Column(name = "street_block")
    String streetBlock;
    @Column(name = "area_group")
    String areaGroup;
    @Column(name = "center")
    String center;
    @Column(name = "pstn_code")
    String pstnCode;
    @Column(name = "province_code")
    String provinceCode;
    @Column(name = "status")
    Long status;
    @Column(name = "create_user")
    String createUser;
    @Column(name = "update_user")
    String updateUser;
    @Column(name = "create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDateTime;
    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDateTime;
}
