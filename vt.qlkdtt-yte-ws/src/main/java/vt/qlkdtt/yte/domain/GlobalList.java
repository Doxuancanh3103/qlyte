package vt.qlkdtt.yte.domain;

import lombok.Data;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.service.sdo.GlobalListInsertSdo;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "GLOBAL_LIST")
public class GlobalList {
    @Id
    @Column(name = "global_list_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "status")
    String status;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "create_user")
    String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    Date createDatetime;

    public GlobalListInsertSdo toGlobalListInsertSdo(){
        GlobalListInsertSdo sdo = new GlobalListInsertSdo();

        sdo.setId(this.getId());
        sdo.setName(this.getName());
        sdo.setCode(this.getCode());
        sdo.setDescription(this.getDescription());
        sdo.setCreateUser(this.getCreateUser());
        sdo.setCreateDatetime(DateUtil.dateyyyyMMdd(this.getCreateDatetime()));

        return sdo;
    }
}
