package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigSearchSdi;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigSearchSdo;

import java.util.List;

public interface MedSaleFeeConfigRepoCustom {
    List<MedSaleFeeConfig> search(String feeConfigCode, String feeConfigName, Long telecomServiceId,
                                  String productOfferCode, String staffCode, Long channelTypeId,
                                  String provinceCode, String status, String fromDate, String toDate);
    boolean isExistFeeForInsert(Long telecomServiceId, String productOfferCode, String channelTypeId);
    boolean isExistFeeForUpdate(Long feeConfigId,Long telecomServiceId,
                                String productOfferCode, String channelTypeId);

    Page<MedSaleFeeConfigSearchSdo> searchMedSaleFeeConfigSdo(MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi, Pageable page);
}
