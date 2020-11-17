package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

import java.util.List;

@Data
public class DoConnectSdi {
    private CustomerInfoDoConnectSdi customerInfo;
    private ProductOfferInfoDoConnectSdi productOfferInfo;
    private ContractInfoDoConnectSdi contractInfo;
    private List<FeeDoConnectSdi> listFee;
    private List<DocumentDoConnectSdi> listDocument;
}
