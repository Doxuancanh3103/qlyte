package vt.qlkdtt.yte.service.impl;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.GlobalParam;
import vt.qlkdtt.yte.domain.Product;
import vt.qlkdtt.yte.repository.AreaRepo;
import vt.qlkdtt.yte.repository.GlobalListRepo;
import vt.qlkdtt.yte.repository.GlobalParamRepo;
import vt.qlkdtt.yte.repository.ProductRepo;
import vt.qlkdtt.yte.service.GlobalParamService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.GenerateAccountSdi;
import vt.qlkdtt.yte.service.sdi.GlobalParamInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalParamUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalParamInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GlobalParamServiceImpl implements GlobalParamService {
    private static final int PARAM_GENERAL = 0;
    private static final int PARAM_OPTION = 1;

    @Autowired
    GlobalParamRepo globalParamRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    AreaRepo areaRepo;

    @Override
    public List<GlobalParam> findByCode(String code) {
        return globalParamRepo.findByCode(code);
    }

    @Override
    public GlobalParam findByCodeAndProduct(String code, Long productId) {
        return globalParamRepo.findByCodeAndProduct(code, productId);
    }

    @Override
    public Long getMaxIndexOfIsdn(String prefix) {
        return globalParamRepo.getMaxIndexOfIsdn(prefix);
    }

    //<editor-fold desc="Change status">
    @Override
    public boolean changeStatus(Long globalParamId, String status) {
        if (DataUtil.isNullObject(globalParamId)) {
            throw new AppException("API-GP001", "Global param id is required");
        }
        if (DataUtil.isNullOrEmpty(status)) {
            throw new AppException("API-GP002", "Global param status is required");
        }
        if (!status.equals(Const.STATUS.INACTIVE) && !status.equals(Const.STATUS.ACTIVE) && !status.equals(Const.STATUS.CANCELED)) {
            throw new AppException("API-GP003", "Global param status must be 0, 1, 2");
        }

        return globalParamRepo.changeStatus(globalParamId, status);
    }
    //</editor-fold>

    //<editor-fold desc="Valid input insert">
    private void validInputInsert(GlobalParamInsertSdi dataInsert) {
        List<String> lstError = new ArrayList<>();

        String type = dataInsert.getType();
        String code = dataInsert.getCode();
        String name = dataInsert.getName();
        String value = dataInsert.getValue();
        String description = dataInsert.getDescription();
        String productGroupId = dataInsert.getProductGroupId();
        Long productId = dataInsert.getProductId();
        String status = dataInsert.getStatus();

        if (!DataUtil.isNullOrEmpty(type) && type.length() > 50) {
            throw new AppException("API-GP004", "Global param type length cannot exceed 50 characters");
        }

        if (!DataUtil.isNullOrEmpty(code) && code.length() > 50) {
            throw new AppException("API-GP005", "Global param code length cannot exceed 50 characters");
        }

        if (!DataUtil.isNullOrEmpty(name) && name.length() > 400) {
            throw new AppException("API-GP006", "Global param name length cannot exceed 400 characters");
        }

        if (!DataUtil.isNullOrEmpty(value) && value.length() > 200) {
            throw new AppException("API-GP007", "Global param value length cannot exceed 200 characters");
        }

        if (!DataUtil.isNullOrEmpty(description) && description.length() > 3000) {
            throw new AppException("API-GP008", "Global param description length cannot exceed 3000 characters");
        }

        if (!DataUtil.isNullOrEmpty(productGroupId)) {
            boolean isExistProductGroup = globalListRepo.isExist(productGroupId, Const.GLOBAL_LIST_CODE.PRODUCT_GROUP);
            if (!isExistProductGroup) {
                lstError.add(productGroupId);
                throw new AppException("API-GP009", "Product group id " + productGroupId + " not exist", lstError);
            }
        }

        if (!DataUtil.isNullObject(productId)) {
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (!optionalProduct.isPresent()) {
                lstError.add(String.valueOf(productId));
                throw new AppException("API-GP0010", "Product id " + productId + " not exist", lstError);
            }
        }

        if (DataUtil.isNullOrEmpty(status)) {
            throw new AppException("API-GP002", "Global param status is required");
        }
        if (!status.equals(Const.STATUS.INACTIVE) && !status.equals(Const.STATUS.ACTIVE)) {
            throw new AppException("API-GP003", "Global param status must be 0 or 1");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Create global param">
    @Override
    public GlobalParamInsertSdo createGlobalParam(GlobalParamInsertSdi dataInsert) {
        validInputInsert(dataInsert);

        GlobalParamInsertSdo result = new GlobalParamInsertSdo();

        GlobalParam gp = dataInsert.toGlobalParam();
        gp = globalParamRepo.save(gp);

        result.setGlobalParamId(gp.getGlobalParamId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Valid input update">
    private void validInputUpdate(GlobalParamUpdateSdi dataUpdate, Optional<GlobalParam> optionalGP) {
        List<String> lstError = new ArrayList<>();

        Long globalParamId = dataUpdate.getGlobalParamId();
        String type = dataUpdate.getType();
        String code = dataUpdate.getCode();
        String name = dataUpdate.getName();
        String value = dataUpdate.getValue();
        String description = dataUpdate.getDescription();
        String productGroupId = dataUpdate.getProductGroupId();
        Long productId = dataUpdate.getProductId();
        String status = dataUpdate.getStatus();

        if (!optionalGP.isPresent()) {
            lstError.add(String.valueOf(globalParamId));
            throw new AppException("API-GP0011", "Global param id {0} not exist", lstError);
        }

        if (!DataUtil.isNullOrEmpty(type) && type.length() > 50) {
            throw new AppException("API-GP004", "Global param type length cannot exceed 50 characters");
        }

        if (!DataUtil.isNullOrEmpty(code) && code.length() > 50) {
            throw new AppException("API-GP005", "Global param code length cannot exceed 50 characters");
        }

        if (!DataUtil.isNullOrEmpty(name) && name.length() > 400) {
            throw new AppException("API-GP006", "Global param name length cannot exceed 400 characters");
        }

        if (!DataUtil.isNullOrEmpty(value) && value.length() > 200) {
            throw new AppException("API-GP007", "Global param value length cannot exceed 200 characters");
        }

        if (!DataUtil.isNullOrEmpty(description) && description.length() > 3000) {
            throw new AppException("API-GP008", "Global param description length cannot exceed 3000 characters");
        }

        if (!DataUtil.isNullOrEmpty(productGroupId)) {
            boolean isExistProductGroup = globalListRepo.isExist(productGroupId, Const.GLOBAL_LIST_CODE.PRODUCT_GROUP);
            if (!isExistProductGroup) {
                lstError.add(productGroupId);
                throw new AppException("API-GP009", "Product group id " + productGroupId + " not exist", lstError);
            }
        }

        if (!DataUtil.isNullObject(productId)) {
            Optional<Product> optionalProduct = productRepo.findById(productId);
            if (!optionalProduct.isPresent()) {
                lstError.add(String.valueOf(productId));
                throw new AppException("API-GP0010", "Product id " + productId + " not exist", lstError);
            }
        }

        if (DataUtil.isNullOrEmpty(status)) {
            throw new AppException("API-GP002", "Global param status is required");
        }
        if (!status.equals(Const.STATUS.INACTIVE) && !status.equals(Const.STATUS.ACTIVE)) {
            throw new AppException("API-GP003", "Global param status must be 0 or 1");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update global param">
    @Override
    public GlobalParamInsertSdo updateGlobalParam(GlobalParamUpdateSdi dataUpdate) {
        Optional<GlobalParam> optionalGP = globalParamRepo.findById(dataUpdate.getGlobalParamId());

        validInputUpdate(dataUpdate, optionalGP);

        GlobalParam gp = optionalGP.get();
        gp = dataUpdate.updateGlobalParam(gp);
        gp = globalParamRepo.save(gp);

        GlobalParamInsertSdo result = new GlobalParamInsertSdo();
        result.setGlobalParamId(gp.getGlobalParamId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="get account index">
    private String genAccountIndex(String account) throws Exception {
        try {
            if (DataUtil.isNullOrEmpty(account)) {
                return "";
            }
            Long maxIndex = getMaxIndexOfIsdnPrefix(account.toLowerCase());

            if (maxIndex != null && maxIndex != -1L) {
                maxIndex++;
                return String.valueOf(maxIndex);
            }
        } catch (Exception ex) {
            throw new AppException("API-GP002", ex.getMessage());
        }
        return "";
    }
    //</editor-fold>

    //<editor-fold desc="get max index of isdn prefix">
    private Long getMaxIndexOfIsdnPrefix(String prefix) throws Exception {
        return globalParamRepo.getMaxIndexOfIsdn(prefix);
    }
    //</editor-fold>

    //<editor-fold desc="retain latin char with under score">
    private String retainLatinCharacterWithUnderScore(String input) throws Exception {
        if (DataUtil.isNullOrEmpty(input)) {
            return "";
        }
        return input.replaceAll("[^a-zA-Z0-9_]", "");
    }

    private String stripAccents(String org) {
        String strippedAccent = DataUtil.convertUnicode2Nosign(StringUtils.stripAccents(org));
        return CharMatcher.WHITESPACE.collapseFrom(strippedAccent, ' ');
    }
    //</editor-fold>

    //<editor-fold desc="Format customer name">
    private String formatCustName(String custName) throws Exception {
        String temp = stripAccents(custName);
        String[] arrayName = temp.split(" ");

        StringBuilder nameTemp = new StringBuilder();
        for (int i = 0; i < arrayName.length; i++) {
            if (!DataUtil.isNullOrEmpty(arrayName[i])) {
                nameTemp.append(arrayName[i].charAt(0));
            }
        }
        return retainLatinCharacterWithUnderScore(nameTemp.toString());
    }
    //</editor-fold>

    //<editor-fold desc="Generate account">
    @Override
    public String generateAccount(GenerateAccountSdi generateAccountSdi) throws Exception {
        if (generateAccountSdi == null) {
            throw new AppException("API-GP003", "Bat buoc nhap gia tri de lay ra Account");
        }

        GlobalParam globalParam = globalParamRepo.findByCodeAndProduct(Const.SUBSCRIBER_ACCOUNT_NO_FORMAT, generateAccountSdi.getProductId());
        String format = "";
        if (DataUtil.isNullObject(globalParam) || DataUtil.isNullObject(globalParam.getGlobalParamId())) {
            throw new AppException("API-GP001", "Chua cau hinh format cho dich vu nay (" + Const.SUBSCRIBER_ACCOUNT_NO_FORMAT + ")");
        } else {
            format = globalParam.getValue();
        }

        ProductFindByIdSdo product = productRepo.searchProductById(DataUtil.safeToLong(generateAccountSdi.getProductId()));
        generateAccountSdi.setProductCode(product.getProductCode());

        String account = setParameters(format, generateAccountSdi, PARAM_GENERAL);

        return account;
    }
    //</editor-fold>

    //<editor-fold desc="Generate account service">
    @Override
    public String generateAccountService(GenerateAccountSdi generateAccountSdi) throws Exception {
        if (generateAccountSdi == null) {
            throw new AppException("API-GP003", "Bat buoc nhap gia tri de lay ra Account");
        }

        GlobalParam globalParam = globalParamRepo.findByCodeAndProduct(Const.SUBSCRIBER_APP_ACCOUNT_NO_FORMAT, generateAccountSdi.getProductId());
        String format = "";
        if (DataUtil.isNullObject(globalParam) || DataUtil.isNullObject(globalParam.getGlobalParamId())) {
            throw new AppException("API-GP001", "Chua cau hinh format cho dich vu nay (" + Const.SUBSCRIBER_APP_ACCOUNT_NO_FORMAT + ")");
        } else {
            format = globalParam.getValue();
        }

        String accountServiceNo = setParameters(format, generateAccountSdi, PARAM_GENERAL);

        return accountServiceNo;
    }
    //</editor-fold>

    //<editor-fold desc="Set parameter">
    private String setParameters(String format, GenerateAccountSdi generateAccountSdi, int type) throws Exception {
        String result = format;

        if (format.indexOf("[") < 0) {
            //not included option
            String[] params = format.split("<|>_<|>");
            Field[] fields = generateAccountSdi.getClass().getDeclaredFields();

            for (String param : params) {
                if (DataUtil.isNullOrEmpty(param)) continue;
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (param.equals(fieldName)) {
                        if (type == PARAM_GENERAL) {
                            if (fieldName.equals("customerName")) {
                                result = result.replace(param, formatCustName(String.valueOf(field.get(generateAccountSdi))));
                            } else {
                                result = result.replace(param, String.valueOf(field.get(generateAccountSdi)));
                            }
                        } else if (type == PARAM_OPTION){
                            if (!DataUtil.isNullOrEmpty(String.valueOf(field.get(generateAccountSdi)))) {
                                return String.valueOf(field.get(generateAccountSdi));
                            }
                        }

                        break;
                    }
                }
            }

            if (type == PARAM_OPTION) return "";

            result = result.replaceAll(">|<", "");

            if (result.indexOf("seq") > 0) {
                String prefix = result.substring(0, result.indexOf("seq") - 1);
                String seq = genAccountIndex(prefix);
                result = prefix + seq;
            }
        } else {
            //include option
            List<String> lstOptions = Arrays.asList(format.split("\\[|\\]"));
            StringBuilder temp = new StringBuilder();

            for (int i = 0; i < lstOptions.size(); i++) {
                if (i % 2 != 0) {
                    String paramOption = lstOptions.get(i);

                    String rsOption = setParameters(paramOption, generateAccountSdi, PARAM_OPTION);
                    lstOptions.set(i, rsOption);
                }
                temp.append(lstOptions.get(i));
            }

            result = setParameters(temp.toString(), generateAccountSdi, PARAM_GENERAL);
        }

        return result;
    }
    //</editor-fold>
}
