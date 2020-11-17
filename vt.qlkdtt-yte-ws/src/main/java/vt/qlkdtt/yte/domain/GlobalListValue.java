package vt.qlkdtt.yte.domain;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.service.sdo.GlobalListInsertSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

@Data
@Entity
@Table(name = "GLOBAL_LIST_VALUE")
public class GlobalListValue {
    @Id
    @Column(name = "global_list_value_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "global_list_id")
    Long globalListId;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

    @Column(name = "value")
    String value;

    @Column(name = "status")
    String status;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "sta_date")
    Date staDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "telco_id")
    Long telcoId;

    @Column(name = "telco_code")
    String telcoCode;

    @Column(name = "telco_value")
    String telcoValue;

    @Column(name = "create_user")
    String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    Date createDatetime;

    @Column(name = "update_user")
    String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_datetime")
    Date updateDatetime;

    @Column(name = "product_group_id")
    Long productGroupId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "display_order")
    Long displayOrder;

    public GlobalListValueSdo toGlobalListValueSdo() throws ParseException {
        GlobalListValueSdo sdo = new GlobalListValueSdo();

        sdo.setId(this.getId());
        sdo.setGlobalListId(this.getGlobalListId());
        sdo.setName(this.getName());
        sdo.setCode(this.getCode());
        sdo.setValue(this.getValue());
        sdo.setDescription(this.getDescription());
        sdo.setStaDate(DateUtil.date2StringByPattern(this.getStaDate(), Const.DATE_FORMAT));
        sdo.setEndDate(DateUtil.date2StringByPattern(this.getEndDate(), Const.DATE_FORMAT));
        sdo.setTelcoId(this.getTelcoId());
        sdo.setTelcoCode(this.getTelcoCode());
        sdo.setTelcoValue(this.getTelcoValue());
        sdo.setCreateUser(this.getCreateUser());
        sdo.setCreateDatetime(DateUtil.date2StringByPattern(this.getCreateDatetime(), Const.DATE_FORMAT));
        sdo.setUpdateUser(this.getUpdateUser());
        sdo.setUpdateDatetime(DateUtil.dateyyyyMMdd(this.getUpdateDatetime()));
        sdo.setProductGroupId(this.getProductGroupId());
        sdo.setProductId(this.getProductId());
        return sdo;
    }
}
