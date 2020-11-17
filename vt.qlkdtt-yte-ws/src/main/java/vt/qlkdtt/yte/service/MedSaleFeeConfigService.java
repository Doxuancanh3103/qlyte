package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigInsertSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigSearchSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigUpdateSdi;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigSearchSdo;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigUpdateSdo;

import java.util.List;

public interface MedSaleFeeConfigService {
    MedSaleFeeConfig insertMedSaleFeeConfig(MedSaleFeeConfigInsertSdi medSaleFeeConfigInsertSdi) throws Exception;
    MedSaleFeeConfigUpdateSdo updateMedSaleFeeConfig(MedSaleFeeConfigUpdateSdi medSaleFeeConfigSdi) throws Exception;
    MedSaleFeeConfigUpdateSdo deleteMedSaleFeeConfig(Long feeConfigId);
    List<MedSaleFeeConfig> search(String feeConfigCode, String feeConfigName, Long telecomServiceId,
                                  String productOfferCode, String staffCode, Long channelTypeId,
                                  String provinceCode, String status, String fromDate, String toDate);

    boolean isExistFeeForInsert(Long telecomServiceId, String productOfferCode, String channelTypeId);
    boolean isExistFeeForUpdate(Long feeConfigId,Long telecomServiceId,
                                String productOfferCode, String channelTypeId);
    Page<MedSaleFeeConfigSearchSdo> searchMedSaleFeeConfig(MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi, Pageable page);
}
