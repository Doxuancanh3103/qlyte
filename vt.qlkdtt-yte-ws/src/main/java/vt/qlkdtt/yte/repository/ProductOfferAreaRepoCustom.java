package vt.qlkdtt.yte.repository;

public interface ProductOfferAreaRepoCustom {
    void changeStatusByProductOffer(String status, long productOfferId);

    boolean isExist(long productOfferAreaId);

    boolean isNationWide(Long productOfferId);
}
