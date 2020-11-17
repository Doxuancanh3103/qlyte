package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "PARTNER_REVENUE_SHARED")
public class PartnerRevenueShared {
    @Id
    @Column(name = "PARTNER_REVENUE_SHARED_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partnerRevenueSharedId;

    @Column(name = "PARTNER_ID")
    private Long partnerId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_OFFER_ID")
    private Long productOfferId;

    @Column(name = "STA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date staDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "SHARE_TYPE")
    private String shareType;

    @Column(name = "VIETTEL_PERCENT")
    private Long viettelPercent;

    @Column(name = "PARTNER_PERCENT")
    private Long partnerPercent;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDatetime;
}
