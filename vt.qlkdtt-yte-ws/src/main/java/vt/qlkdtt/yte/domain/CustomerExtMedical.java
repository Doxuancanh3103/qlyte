package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_EXT_MEDICAL")
public class CustomerExtMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "MEDICAL_PERMISSION_NO")
    private String medicalPermissionNo;

    @Column(name = "MEDICAL_ESTABLISH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date medicalEstablishDate;

    @Column(name = "CUSTOMER_IDENTITY_ID")
    private Long customerIdentityId;

    @Column(name = "MEDICAL_IDENTITY_NO")
    private String medicalIdentityNo;
}
