package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;

import java.util.List;

public interface AreaService {
    public List<AreaDTO> getProvinceList();

    public List<AreaDTO> getDistrictListByProvince(String province);
    public List<AreaDTO> getDistrictListByProvince1(String province);

    public List<AreaDTO> getPrecinctListByDistrict(String district);

    public AreaFullDTO getAreaByCode(String areaCode);
    

    AreaDTO getProvinceByDistrictAreaCode(String districtAreaCode);

    AreaDTO getDistrictByDistrictAreaCode(String districtAreaCode);

    boolean isExist(String areaCode);

    boolean isPrecinctAreaCode(String areaCode);

    boolean isDistrictAreaCode(String areaCode);
}
