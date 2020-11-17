package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.CustomerContract;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdi.ContractUpdateSdi;
import vt.qlkdtt.yte.service.sdo.ContractFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchSdo;

public interface ContractService {
    Page<ContractSearchSdo> searchContract(ContractSearchSdi contractSearchSdi, Pageable pageable) throws Exception;

    Page<ContractSearchConnectSdo> searchContractByCustomer(ContractSearchSdi contractSearchSdi, Pageable pageable) throws Exception;

    ContractFindByIdSdo findById(long customerContractId);

    CustomerContract updateContract(ContractUpdateSdi contractUpdateSdi);

    CustomerContract liquidateContract(long contractId);

    CustomerContract extensionContract(long contractId);
}
