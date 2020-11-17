package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "GLOBAL_PARAM")
public class GlobalParam {
    @Id
    @Column(name = "GLOBAL_PARAM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long globalParamId;

    @Column(name = "TYPE")
    String type;

    @Column(name = "CODE")
    String code;

    @Column(name = "NAME")
    String name;

    @Column(name = "VALUE")
    String value;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "PRODUCT_GROUP_ID")
    String productGroupId;

    @Column(name = "PRODUCT_ID")
    Long productId;

    @Column(name = "STATUS")
    String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    Date createDate;

    @Column(name = "CREATE_USER")
    String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    Date updateDate;

    @Column(name = "UPDATE_USER")
    String updateUser;

}
