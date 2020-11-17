package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.repository.AreaRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AreaRepoCustomImpl implements AreaRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Get province list">
    @Override
    public List<AreaDTO> getProvinceList() {
        List<AreaDTO> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();

        strQuery.append(" SELECT");
        strQuery.append("   area_code,name,full_name,province,parent_code ");
        strQuery.append(" FROM ");
        strQuery.append("   AREA ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("   AND parent_code is null and status = '1' ");
        strQuery.append(" ORDER BY name COLLATE utf8_unicode_ci ");

        Query query = em.createNativeQuery(strQuery.toString());
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {
                AreaDTO dto = new AreaDTO();
                dto = DataUtil.convertObjectsToClass(obj, dto);

                result.add(dto);
            }
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get district list by province">
    @Override
    public List<AreaDTO> getDistrictListByProvince(String province) {
        List<AreaDTO> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append(" SELECT");
        strQuery.append("   area_code,name,full_name,district,parent_code ");
        strQuery.append(" FROM ");
        strQuery.append("   AREA ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("   AND district is not null and precinct is null and status = '1' ");

        if (!DataUtil.isStringNullOrEmpty(province)) {
            strQuery.append(" AND province = :province");
            parameterMap.put("province", province);
        }

        strQuery.append(" ORDER BY province, name COLLATE utf8_unicode_ci ");

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {
                AreaDTO dto = new AreaDTO();
                dto = DataUtil.convertObjectsToClass(obj, dto);
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<AreaDTO> getDistrictListByProvince1(String province) {
        List<AreaDTO> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append(" SELECT");
        strQuery.append("   area_code,name,full_name,district,parent_code ");
        strQuery.append(" FROM ");
        strQuery.append("   AREA ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("   AND district is not null and precinct is null and status = '1' ");

        if (!DataUtil.isStringNullOrEmpty(province)) {
            strQuery.append(" AND province = :province");
            parameterMap.put("province", province);
        }

        strQuery.append(" ORDER BY province, name COLLATE utf8_unicode_ci ");

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {
                AreaDTO dto = new AreaDTO();
                dto = DataUtil.convertObjectsToClass(obj, dto);

                result.add(dto);
            }
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get precinct list by district">
    @Override
    public List<AreaDTO> getPrecinctListByDistrict(String district) {
        List<AreaDTO> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append(" SELECT");
        strQuery.append("   area_code,name,full_name,precinct,parent_code ");
        strQuery.append(" FROM ");
        strQuery.append("   AREA ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("   AND precinct is not null and street_block is null and status = '1' ");

        if (!DataUtil.isStringNullOrEmpty(district)) {
            strQuery.append(" AND concat(province, district) = :district");
            parameterMap.put("district", district);
        }
        strQuery.append(" ORDER BY province , district , name COLLATE utf8_unicode_ci");

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {
                AreaDTO dto = new AreaDTO();
                dto = DataUtil.convertObjectsToClass(obj, dto);

                result.add(dto);
            }
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get area by code">
    @Override
    public AreaFullDTO getAreaByCode(String areaCode) {
        AreaFullDTO result = new AreaFullDTO();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append(" SELECT");
        strQuery.append("   area_code,name,full_name,province,district,precinct,parent_code ");
        strQuery.append(" FROM ");
        strQuery.append("   AREA ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("   AND area_code = :areaCode and status = '1' ");
        parameterMap.put("areaCode", areaCode);

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {
                result = DataUtil.convertObjectsToClass(obj, result);
            }
        }
        return result;
    }
    //</editor-fold>

    private AreaDTO convertObjectsToAreaDTO(Object[] objects) {
        AreaDTO result = new AreaDTO();

        result.setAreaCode(DataUtil.safeToString(objects[0]));
        result.setName(DataUtil.safeToString(objects[1]));
        result.setFullName(DataUtil.safeToString(objects[2]));
        result.setCode(DataUtil.safeToString(objects[3]));
        result.setParentCode(DataUtil.safeToString(objects[4]));

        return result;
    }

    @Override
    public AreaDTO getProvinceByDistrictAreaCode(String districtAreaCode) {
        AreaDTO result = new AreaDTO();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT AREA_CODE, NAME, FULL_NAME, PROVINCE, PARENT_CODE ");
        sql.append("FROM AREA ");
        sql.append("WHERE AREA_CODE = (SELECT PARENT_CODE FROM AREA WHERE AREA_CODE = :districtAreaCode)");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("districtAreaCode", districtAreaCode);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult){
            result = convertObjectsToAreaDTO(item);
        }

        return result;
    }

    @Override
    public AreaDTO getDistrictByDistrictAreaCode(String districtAreaCode) {
        AreaDTO result = new AreaDTO();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT AREA_CODE, NAME, FULL_NAME, DISTRICT, PARENT_CODE ");
        sql.append("FROM AREA ");
        sql.append("WHERE AREA_CODE = :districtAreaCode");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("districtAreaCode", districtAreaCode);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = convertObjectsToAreaDTO(item);
        }

        return result;
    }

    @Override
    public boolean isExist(String areaCode) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM AREA WHERE AREA_CODE = :areaCode");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("areaCode", areaCode);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isPrecinctAreaCode(String areaCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM AREA ");
        sql.append("WHERE AREA_CODE = :areaCode ");
        sql.append("AND PROVINCE IS NOT NULL ");
        sql.append("AND DISTRICT IS NOT NULL ");
        sql.append("AND PRECINCT IS NOT NULL ");
        sql.append("AND STREET_BLOCK IS NULL ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("areaCode", areaCode);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isDistrictAreaCode(String areaCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM AREA ");
        sql.append("WHERE AREA_CODE = :areaCode ");
        sql.append("AND PROVINCE IS NOT NULL ");
        sql.append("AND DISTRICT IS NOT NULL ");
        sql.append("AND PRECINCT IS NULL ");
        sql.append("AND STREET_BLOCK IS NULL ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("areaCode", areaCode);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
