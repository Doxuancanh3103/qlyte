package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "med_sale_fee_config")
public class MedSaleFeeConfig {
    @Id
    @Column(name = "FEE_CONFIG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feeConfigId;
    @Column(name = "FEE_CONFIG_CODE")
    private String feeConfigCode;
    @Column(name = "FEE_CONFIG_NAME")
    private String feeConfigName;
    @Column(name = "FEE_CONFIG_DESCRIPTION")
    private String feeConfigDescription;
    @Column(name = "TELECOM_SERVICE_ID")
    private Long telecomServiceId;
    @Column(name = "PRODUCT_OFFER_CODE")
    private String productOfferCode;
    @Column(name = "STAFF_CODE")
    private String staffCode;
    @Column(name = "CHANNEL_TYPE_ID")
    private String channelTypeId;
    @Column(name = "PROVINCE_CODE")
    private String provinceCode;
    @Column(name = "SALE_LEVEL")
    private Long saleLevel;
    @Column(name = "FEE_VALUE")
    private Long feeValue;
    @Column(name = "FEE_TYPE")
    private Integer feeType;
    @Column(name = "STA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "UPDATE_USER")
    private String updateUser;
    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}
