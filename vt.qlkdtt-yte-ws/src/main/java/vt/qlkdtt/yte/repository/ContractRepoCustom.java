package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdo.ContractFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchSdo;

public interface ContractRepoCustom {
    Page<ContractSearchSdo> searchContract(ContractSearchSdi contractSearchSdi, Pageable page);

    Page<ContractSearchConnectSdo> searchContractConnect(ContractSearchSdi contractSearchSdi, Pageable page);

    public ContractFindByIdSdo findById(long customerContractId);

    public boolean isExits(String contractNo, long contractId);

    public boolean updateBillInfo(String billNotificationMethod, String billAddress, long customerId);

    Long getAccountId(Long contractId);
}
