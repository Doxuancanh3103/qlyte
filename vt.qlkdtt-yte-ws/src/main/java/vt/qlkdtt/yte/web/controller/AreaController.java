package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.service.AreaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "area")
@Api(value = "Area")
public class AreaController {
    @Autowired
    AreaService areaService;

    @GetMapping(value = "getProvinceList")
    @ApiOperation(value = "search list full province")
    public ResponseEntity<List<AreaDTO>> getProvinceList() {
        List<AreaDTO> lstArea = areaService.getProvinceList();
        if (lstArea != null && !lstArea.isEmpty()) {
            return ResponseEntity.ok(lstArea);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "getDistrictListByProvince")
    @ApiOperation(value = "search list district by province")
    public ResponseEntity<List<AreaDTO>> getDistrictListByProvince(
            @RequestParam(value = "province", required = false) String province) {
        List<AreaDTO> lstArea = areaService.getDistrictListByProvince(province);
        if (lstArea != null && !lstArea.isEmpty()) {
            return ResponseEntity.ok(lstArea);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "getDistrictListByProvince1")
    @ApiOperation(value = "search list district by province")
    public ResponseEntity<List<AreaDTO>> getDistrictListByProvince1(
            @RequestParam(value = "province", required = false) String province) {
        List<AreaDTO> lstArea = areaService.getDistrictListByProvince(province);
        if (lstArea != null && !lstArea.isEmpty()) {
            return ResponseEntity.ok(lstArea);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "getPrecinctListByDistrict")
    @ApiOperation(value = "search list precinct by district")
    public ResponseEntity<List<AreaDTO>> getPrecinctListByDistrict(
            @RequestParam(value = "provinceDistrict", required = false) String district) {
        List<AreaDTO> lstArea = areaService.getPrecinctListByDistrict(district);
        if (lstArea != null && !lstArea.isEmpty()) {
            return ResponseEntity.ok(lstArea);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "getAreaByCode")
    @ApiOperation(value = "search area by areaCode")
    public ResponseEntity<AreaFullDTO> getAreaByCode(
            @RequestParam(value = "getAreaByCode", required = false) String areaCode) {
        AreaFullDTO areaDTO = areaService.getAreaByCode(areaCode);
        if (areaDTO != null && !DataUtil.isNullOrEmpty(areaDTO.getAreaCode())) {
            return ResponseEntity.ok(areaDTO);
        }
        return ResponseEntity.noContent().build();
    }

//    @PostMapping(value = "testException")
//    @ApiOperation(value = "Do testException")
//    public ResponseEntity<?> testException(@RequestBody ErrorMessage errorMessage) {
//        throw new AppException(errorMessage.getMessage(), errorMessage.getMessage(), errorMessage.getErrorField());
//    }

}
