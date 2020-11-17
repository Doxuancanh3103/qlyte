package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.SubscriberRepo;
import vt.qlkdtt.yte.repository.SubscriberRepoCustom;
import vt.qlkdtt.yte.service.SubscriberService;
import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import java.util.List;

@Service
@Transactional
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    SubscriberRepoCustom subscriberRepoCustom;

    @Override
    public SubscriberFindByIdSdo findById(long subscriberId) {
        SubscriberFindByIdSdo result = subscriberRepoCustom.findById(subscriberId);

        return result;
    }

    @Override
    public boolean changeSalesManCode(String salesManCode, Long subscriberId) {
        return subscriberRepoCustom.changeSalesManCode(salesManCode, subscriberId);
    }

    @Override
    public boolean updateBrokersPartnerCode(String brokersPartnerCode, Long subscriberId) {
        return subscriberRepoCustom.updateBrokersPartnerCode(brokersPartnerCode, subscriberId);
    }
}
