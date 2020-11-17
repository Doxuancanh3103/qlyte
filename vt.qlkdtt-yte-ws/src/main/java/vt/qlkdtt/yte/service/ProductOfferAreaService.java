package vt.qlkdtt.yte.service;

public interface ProductOfferAreaService {
    void changeStatusByProductOffer(String status, long productOfferId);

    boolean isExist(long productOfferAreaId);

    boolean isNationWide(Long productOfferId);
}
