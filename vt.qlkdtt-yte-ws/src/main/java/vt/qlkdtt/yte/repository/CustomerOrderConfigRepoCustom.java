package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.domain.CustomerOrderConfig;

public interface CustomerOrderConfigRepoCustom {
    CustomerOrderConfig search(String orderType, String actionTypeId, Long productId);
}
