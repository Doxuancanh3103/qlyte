package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;

import java.util.List;

public interface AreaRepoCustom {
    List<AreaDTO> getProvinceList();

    List<AreaDTO> getDistrictListByProvince(String province);
    List<AreaDTO> getDistrictListByProvince1(String province);

    List<AreaDTO> getPrecinctListByDistrict(String district);

    AreaFullDTO getAreaByCode(String areaCode);

    AreaDTO getProvinceByDistrictAreaCode(String districtAreaCode);

    AreaDTO getDistrictByDistrictAreaCode(String districtAreaCode);

    boolean isExist(String areaCode);

    boolean isPrecinctAreaCode(String areaCode);

    boolean isDistrictAreaCode(String areaCode);
}
