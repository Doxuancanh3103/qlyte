package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractFindByIdSdo {
    //    Thông tin hợp đồng
    private long contractId;
    private String contractNo;
    private String contractStatus;
    private String signDate;
    private String billNotificationMethod;
    private String billNotificationAddress;
    private String billAddress;
    private String representName;
    private String representIdNo;
    private String representTitle;
    private String representEmail;
    private String representTel;
    private String paymentMethod;
    //    Thông tin khách hàng
    private long customerId;
    private String customerBusType;
    private String customerType;
    private String customerName;
    private String medicalPermissionNo;
    private String customerIdentityNo;
    private String birthDate;
    private String tel;
    private String email;
    private String province;
    private String district;
    private String precinct;
    private String provinceName;
    private String districtName;
    private String precinctName;
    private String address;
    private String customerRepresentName;
    private String customerRepresentIdNo;
    private String customerRepresentTitle;
    private String customerRepresentTel;
    private String customerRepresentEmail;
    private Long accountId;
    private String accountNo;
    private String productCustSegmId;
    //    Thông tin dịch vụ
    List<SubscriberSdo> lstSubscriberSdos = new ArrayList<>();
    //    Thông tin hồ sơ
    List<DocumentSdo> lstDocumentSdos = new ArrayList<>();
}
