package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import java.util.List;

public interface SubscriberService {
    SubscriberFindByIdSdo findById(long subscriberId);

    boolean changeSalesManCode(String salesManCode, Long subscriberId);

    boolean updateBrokersPartnerCode(String brokersPartnerCode, Long subscriberId);
}
