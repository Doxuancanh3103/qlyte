package vt.qlkdtt.yte.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "PRODUCT_CUST_SEGM")
public class ProductCustSegm {
    @Id
    @Column(name = "product_cust_segm_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long productCustSegmId;

    @Column(name = "global_value")
    String globalValue;

    @Transient
    private String globalName;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "product_offer_id")
    Long productOfferId;

    @Column(name = "sta_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date staDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date endDate;

    @Column(name = "status")
    String status;

    @Column(name = "create_user")
    String createUser;

    @Column(name = "create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDatetime;

    @Column(name = "update_user")
    String updateUser;

    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDatetime;
}
