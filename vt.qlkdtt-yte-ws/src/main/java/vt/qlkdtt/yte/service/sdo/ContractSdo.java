package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.domain.CustomerContract;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ContractSdo {

    long contractId;
    String contractNo;

    public ContractSdo toContractSdo(CustomerContract customerContract) {
        return ContractSdo.builder()
                .contractId(customerContract.getContractId())
                .contractNo(customerContract.getContractNo())
                .build();
    }
}
