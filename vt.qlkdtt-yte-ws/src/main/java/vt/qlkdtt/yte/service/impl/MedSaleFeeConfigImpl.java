package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;
import vt.qlkdtt.yte.repository.MedSaleFeeConfigRepo;
import vt.qlkdtt.yte.service.MedSaleFeeConfigService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigInsertSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigSearchSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigUpdateSdi;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigSearchSdo;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigUpdateSdo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MedSaleFeeConfigImpl implements MedSaleFeeConfigService {
    @Autowired
    MedSaleFeeConfigRepo medSaleFeeConfigRepo;
    @Override
    public MedSaleFeeConfig insertMedSaleFeeConfig(MedSaleFeeConfigInsertSdi medSaleFeeConfigInsertSdi) throws Exception {
        if (medSaleFeeConfigInsertSdi.getTelecomServiceId() == null){
            throw new AppException("API-MSFC006");
        }
        validateMedSaleFeeConfigForInsert(medSaleFeeConfigInsertSdi);
        if (this.isExistFeeForInsert(medSaleFeeConfigInsertSdi.getTelecomServiceId(),
                                     medSaleFeeConfigInsertSdi.getProductOfferCode(),
                                     medSaleFeeConfigInsertSdi.getChannelTypeId())){
            throw new AppException("API-MSFC005");
        }
        return medSaleFeeConfigRepo.save(medSaleFeeConfigInsertSdi.convertToMedSaleFeeConfig());
    }
    private void _updateMedSaleFeeConfig(MedSaleFeeConfig medSaleFeeConfig, MedSaleFeeConfigUpdateSdi dataUpdate) throws Exception {
        if (dataUpdate.getTelecomServiceId() == null){
            throw new AppException("API-MSFC006");
        }
        validateMedSaleFeeConfigForUpdate(dataUpdate);
        if (this.isExistFeeForUpdate(medSaleFeeConfig.getFeeConfigId(),
                medSaleFeeConfig.getTelecomServiceId(),
                medSaleFeeConfig.getProductOfferCode(),
                medSaleFeeConfig.getChannelTypeId())){
            throw new AppException("API-MSFC005");
        }
        medSaleFeeConfigRepo.save( dataUpdate.convertToMedSaleFeeConfig());
    }
    @Override
    public MedSaleFeeConfigUpdateSdo updateMedSaleFeeConfig(MedSaleFeeConfigUpdateSdi medSaleFeeConfigUpdateSdi) throws Exception {
        Optional<MedSaleFeeConfig> optional = medSaleFeeConfigRepo.findById(medSaleFeeConfigUpdateSdi.getFeeConfigId());
        MedSaleFeeConfigUpdateSdo result = new MedSaleFeeConfigUpdateSdo();
        if (optional.isPresent()){
            MedSaleFeeConfig medSaleFeeConfig = optional.get();

            _updateMedSaleFeeConfig(medSaleFeeConfig,medSaleFeeConfigUpdateSdi);

            result.setFeeConfigId(medSaleFeeConfig.getFeeConfigId());
        }
        return result;
    }

    @Override
    public MedSaleFeeConfigUpdateSdo deleteMedSaleFeeConfig(Long feeConfigId) {
        MedSaleFeeConfigUpdateSdo result = new MedSaleFeeConfigUpdateSdo();
        Optional<MedSaleFeeConfig> optional = medSaleFeeConfigRepo.findById(feeConfigId);
        if (optional.isPresent()){
            MedSaleFeeConfig medSaleFeeConfig = optional.get();
            medSaleFeeConfig.setStatus("0");
            result.setFeeConfigId(medSaleFeeConfig.getFeeConfigId());
            medSaleFeeConfigRepo.save(medSaleFeeConfig);
        }
        return result;
    }

    @Override
    public List<MedSaleFeeConfig> search(String feeConfigCode, String feeConfigName, Long telecomServiceId, String productOfferCode, String staffCode, Long channelTypeId, String provinceCode, String status, String fromDate, String toDate) {
        return medSaleFeeConfigRepo.search(feeConfigCode,feeConfigName,telecomServiceId,productOfferCode,staffCode,channelTypeId,provinceCode,status,fromDate,toDate);
    }

    @Override
    public boolean isExistFeeForInsert(Long telecomServiceId, String productOfferCode, String channelTypeId) {
        return medSaleFeeConfigRepo.isExistFeeForInsert(telecomServiceId, productOfferCode, channelTypeId);
    }

    @Override
    public boolean isExistFeeForUpdate(Long feeConfigId, Long telecomServiceId,
                                       String productOfferCode, String channelTypeId) {
        return medSaleFeeConfigRepo.isExistFeeForUpdate(feeConfigId,telecomServiceId,
                productOfferCode,channelTypeId);
    }

    @Override
    public Page<MedSaleFeeConfigSearchSdo> searchMedSaleFeeConfig(MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi, Pageable page) {
//        if (DataUtil.isNullOrZero(medSaleFeeConfigSearchSdi.getTelecomServiceId())){
//            throw new AppException("API-MSFC006");
//        }

        if (DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getFromDate())){
            throw new AppException("API-MSFC008");
        }
        if (DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getToDate())){
            throw  new AppException("API-MSFC008");
        }
        if (!DateUtil.isDate(medSaleFeeConfigSearchSdi.getFromDate(),"yyyy-MM-dd")){
            throw new AppException("API-MSFC004");
        }
        if (!DateUtil.isDate(medSaleFeeConfigSearchSdi.getToDate(),"yyyy-MM-dd")){
            throw new AppException("API-MSFC004");
        }
        if(!DataUtil.isNullOrZero(medSaleFeeConfigSearchSdi.getTelecomServiceId())){
            if (checkNumber(medSaleFeeConfigSearchSdi.getTelecomServiceId())){
                throw new AppException("API-MSFC001");
            }
        }
        return medSaleFeeConfigRepo.searchMedSaleFeeConfigSdo(medSaleFeeConfigSearchSdi,page);
    }

    private boolean checkNumber(Object number){
        if (number instanceof Float) {
            Float castNumber = (Float)number;
            return castNumber >= 0?true:false;
        }else if (number instanceof Double){
            Double castNumber = (Double)number;
            return castNumber >= 0?true:false;
        }else if (number instanceof Integer){
            Integer castNumber = (Integer)number;
            return castNumber >= 0?true:false;
        }
        Long castNumber = (Long)number;
        return castNumber >= 0?true:false;
    }

    private boolean checkNull(Object object){
       return object == null ? true : false;
    }

    private boolean checkDate(String date){
//        String DATE_REGEX = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";
//        return Pattern.matches(DATE_REGEX,date);
        return DateUtil.isDate(date,"yyyy-MM-dd");
    }

    private boolean checkEmail(String email){
        String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(EMAIL_REGEX,email);
    }


    private boolean checkNumberPhone(String phonenumber){
        String NUMBERPHONE_REGEX = "((09|03|07|08|05)+([0-9]{8})\\b)";
        String NEW_NUMBERPHONE_REGEX = "((035)+([0-9]{7}))\\b";

        if (Pattern.matches(NEW_NUMBERPHONE_REGEX,phonenumber)){
            return true;
        }
        return Pattern.matches(NUMBERPHONE_REGEX,phonenumber);
    }

    private boolean checkMaxLength(String string,long max){
        return string.length() <= max ? true : false;
    }


    private void validateMedSaleFeeConfigForInsert(MedSaleFeeConfigInsertSdi medSaleFeeConfigInsertSdi) throws Exception {


        // validateNumber
        if (!checkNumber(medSaleFeeConfigInsertSdi.getFeeValue())) throw new AppException("API-MSFC001","feeValue cannot be a negative number");
        if (!checkNumber(medSaleFeeConfigInsertSdi.getSaleLevel())) throw new AppException("API-MSFC001","saleLevel cannot be a negative number");
        if (!checkNumber(medSaleFeeConfigInsertSdi.getTelecomServiceId())) throw new AppException("API-MSFC001","telecomServiceId cannot be a negative number");

        //validateDate
        if (!checkDate(medSaleFeeConfigInsertSdi.getStartDate())){
            throw new AppException("API-MSFC004");
        }

        if(!checkDate(medSaleFeeConfigInsertSdi.getEndDate())){
            throw new AppException("API-MSFC004");
        }

        //validateString

        if (medSaleFeeConfigInsertSdi.getChannelTypeId() != null && !"".equals(medSaleFeeConfigInsertSdi.getChannelTypeId())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getChannelTypeId(),100)){
                throw new AppException("API-MSFC003","string channelTypeId can only maximum 100 characters");
            }
        }
        if (!"1".equals(medSaleFeeConfigInsertSdi.getStatus()) && !"0".equals(medSaleFeeConfigInsertSdi.getStatus())){
            throw new AppException("API-MSFC002","Status accepts only 0 or 1");
        }

        if (medSaleFeeConfigInsertSdi.getFeeType() != 1 && medSaleFeeConfigInsertSdi.getFeeType() != 2){
            throw new AppException("API-MSFC002","feeType accepts only 1 or 2");
        }

        if (medSaleFeeConfigInsertSdi.getFeeConfigCode() != null && !"".equals(medSaleFeeConfigInsertSdi.getFeeConfigCode())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getFeeConfigCode(),100)) {
                throw new AppException("API-MSFC003","string feeConfigCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigInsertSdi.getFeeConfigName() != null && !"".equals(medSaleFeeConfigInsertSdi.getFeeConfigName())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getFeeConfigName(),100)){
                throw new AppException("API-MSFC003","string feeConfigName can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigInsertSdi.getFeeConfigDescription() != null && !"".equals(medSaleFeeConfigInsertSdi.getFeeConfigDescription())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getFeeConfigDescription(),200)){
                throw new AppException("API-MSFC003","string feeConfigDescription can only maximum 200 characters");
            }
        }

        if (medSaleFeeConfigInsertSdi.getProductOfferCode() != null && !"".equals(medSaleFeeConfigInsertSdi.getProductOfferCode())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getProductOfferCode(),100)){
                throw new AppException("API-MSFC003","string productOfferCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigInsertSdi.getStaffCode() != null && !"".equals(medSaleFeeConfigInsertSdi.getStaffCode())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getStaffCode(),100)){
                throw new AppException("API-MSFC003","string staffCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigInsertSdi.getProvinceCode() != null && !"".equals(medSaleFeeConfigInsertSdi.getProvinceCode())){
            if (!checkMaxLength(medSaleFeeConfigInsertSdi.getProvinceCode(),50)){
                throw new AppException("API-MSFC003","string provinceCode can only maximum 50 characters");
            }
        }

        if (new SimpleDateFormat("yyyy-MM-dd").parse(medSaleFeeConfigInsertSdi.getStartDate()).after(new SimpleDateFormat("yyyy-MM-dd").parse(medSaleFeeConfigInsertSdi.getEndDate()))){
            throw new AppException("API-MSFC007");
        }
    }


    private void validateMedSaleFeeConfigForUpdate(MedSaleFeeConfigUpdateSdi medSaleFeeConfigUpdateSdi) throws Exception {


        // validateNumber
        if (!checkNumber(medSaleFeeConfigUpdateSdi.getFeeValue())) throw new AppException("API-MSFC001","feeValue cannot be a negative number");
        if (!checkNumber(medSaleFeeConfigUpdateSdi.getSaleLevel())) throw new AppException("API-MSFC001","saleLevel cannot be a negative number");
        if (!checkNumber(medSaleFeeConfigUpdateSdi.getTelecomServiceId())) throw new AppException("API-MSFC001","telecomServiceId cannot be a negative number");

        //validateDate
        if (!checkDate(medSaleFeeConfigUpdateSdi.getStartDate())){
            throw new AppException("API-MSFC004");
        }

        if(!checkDate(medSaleFeeConfigUpdateSdi.getEndDate())){
            throw new AppException("API-MSFC004");
        }

        //validateString

        if (medSaleFeeConfigUpdateSdi.getChannelTypeId() != null && !"".equals(medSaleFeeConfigUpdateSdi.getChannelTypeId())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getChannelTypeId(),100)){
                throw new AppException("API-MSFC003","string channelTypeId can only maximum 100 characters");
            }
        }
        if (!"1".equals(medSaleFeeConfigUpdateSdi.getStatus()) && !"0".equals(medSaleFeeConfigUpdateSdi.getStatus())){
            throw new AppException("API-MSFC002","Status accepts only 0 or 1");
        }

        if (medSaleFeeConfigUpdateSdi.getFeeType() != 1 && medSaleFeeConfigUpdateSdi.getFeeType() != 2){
            throw new AppException("API-MSFC002","feeType accepts only 1 or 2");
        }

        if (medSaleFeeConfigUpdateSdi.getFeeConfigCode() != null && !"".equals(medSaleFeeConfigUpdateSdi.getFeeConfigCode())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getFeeConfigCode(),100)) {
                throw new AppException("API-MSFC003","string feeConfigCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigUpdateSdi.getFeeConfigName() != null && !"".equals(medSaleFeeConfigUpdateSdi.getFeeConfigName())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getFeeConfigName(),100)){
                throw new AppException("API-MSFC003","string feeConfigName can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigUpdateSdi.getFeeConfigDescription() != null && !"".equals(medSaleFeeConfigUpdateSdi.getFeeConfigDescription())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getFeeConfigDescription(),200)){
                throw new AppException("API-MSFC003","string feeConfigDescription can only maximum 200 characters");
            }
        }

        if (medSaleFeeConfigUpdateSdi.getProductOfferCode() != null && !"".equals(medSaleFeeConfigUpdateSdi.getProductOfferCode())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getProductOfferCode(),100)){
                throw new AppException("API-MSFC003","string productOfferCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigUpdateSdi.getStaffCode() != null && !"".equals(medSaleFeeConfigUpdateSdi.getStaffCode())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getStaffCode(),100)){
                throw new AppException("API-MSFC003","string staffCode can only maximum 100 characters");
            }
        }

        if (medSaleFeeConfigUpdateSdi.getProvinceCode() != null && !"".equals(medSaleFeeConfigUpdateSdi.getProvinceCode())){
            if (!checkMaxLength(medSaleFeeConfigUpdateSdi.getProvinceCode(),50)){
                throw new AppException("API-MSFC003","string provinceCode can only maximum 50 characters");
            }
        }

        if (new SimpleDateFormat("yyyy-MM-dd").parse(medSaleFeeConfigUpdateSdi.getStartDate()).after(new SimpleDateFormat("yyyy-MM-dd").parse(medSaleFeeConfigUpdateSdi.getEndDate()))){
            throw new AppException("API-MSFC007");
        }
    }
}
