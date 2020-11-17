package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import java.util.List;

public interface SubscriberRepoCustom {
    SubscriberFindByIdSdo findById(Long subscriberId);

    boolean changeSalesManCode(String salesManCode, Long subscriberId);

    boolean updateBrokersPartnerCode(String brokersPartnerCode, Long subscriberId);
}
