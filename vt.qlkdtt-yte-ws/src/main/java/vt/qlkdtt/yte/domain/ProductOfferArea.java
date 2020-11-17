package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PRODUCT_OFFER_AREA")
public class ProductOfferArea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_OFFER_AREA_ID")
    private Long productOfferAreaId;

    @Column(name = "PRODUCT_OFFER_ID")
    private Long productOfferId;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "STA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date staDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

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
