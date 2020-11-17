package vt.qlkdtt.yte.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long productId;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_code")
    String productCode;

    @Column(name = "description")
    String description;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    Date createDate;

    @Column(name = "effect_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date effectDate;

    @Column(name = "expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date expireDate;

    @Column(name = "status")
    String status;

    @Column(name = "product_type")
    String productType;

    @Column(name = "product_group_id")
    String productGroupId;

    @Column(name = "telco_service_id")
    Long telcoServiceId;

    @Column(name = "GL_CODE_1")
    String glCode1;

    @Column(name = "GL_CODE_2")
    String glCode2;

    @Column(name = "SALES_ITEM_CODE")
    String salesItemCode;
}
