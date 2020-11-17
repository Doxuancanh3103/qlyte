package vt.qlkdtt.yte.domain;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT_REQUIRED")
public class DocumentRequired {
    @Id
    @Column(name = "DOCUMENT_REQUIRED_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doucmentRequiredId;

    @Column(name = "ORDER_TYPE")
    private String orderType;

    @Column(name = "ACTION_TYPE")
    private String actionType;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_GROUP_ID")
    private String productGroupId;

    @Column(name = "DOCUMENT_TYPE_REQUIRED")
    private String documentTypeRequired;
}
