package vt.qlkdtt.yte.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.service.AreaService;
import vt.qlkdtt.yte.repository.AreaRepo;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaRepo areaRepo;

    @Override
    public List<AreaDTO> getProvinceList() {
        List<AreaDTO> lstArea = areaRepo.getProvinceList();
        return lstArea;
    }

    @Override
    public List<AreaDTO> getDistrictListByProvince(String province) {
        List<AreaDTO> lstArea = areaRepo.getDistrictListByProvince(province);
        return lstArea;
    }

    @Override
    public List<AreaDTO> getDistrictListByProvince1(String province) {
        List<AreaDTO> lstArea = areaRepo.getDistrictListByProvince(province);
        return lstArea;
    }

    @Override
    public List<AreaDTO> getPrecinctListByDistrict(String district) {
        List<AreaDTO> lstArea = areaRepo.getPrecinctListByDistrict(district);
        return lstArea;
    }

    @Override
    public AreaFullDTO getAreaByCode(String areaCode) {
        AreaFullDTO areaDTO = areaRepo.getAreaByCode(areaCode);
        return areaDTO;
    }

    @Override
    public AreaDTO getProvinceByDistrictAreaCode(String districtAreaCode) {
        return areaRepo.getProvinceByDistrictAreaCode(districtAreaCode);
    }

    @Override
    public AreaDTO getDistrictByDistrictAreaCode(String districtAreaCode) {
        return areaRepo.getDistrictByDistrictAreaCode(districtAreaCode);
    }

    @Override
    public boolean isExist(String areaCode) {
        return areaRepo.isExist(areaCode);
    }

    @Override
    public boolean isPrecinctAreaCode(String areaCode) {
        return areaRepo.isPrecinctAreaCode(areaCode);
    }

    @Override
    public boolean isDistrictAreaCode(String areaCode) {
        return areaRepo.isDistrictAreaCode(areaCode);
    }

}
