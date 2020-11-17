package vt.qlkdtt.yte.common;

public class Const {
    public final static String CHECK_ISDN_ADD_PREFIX = "^0+(?!$)|^84(?!$)";

    public final static String REGEX_BASE64 = "^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}==|[A-Za-z0-9+\\/]{3}=)?$";

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT = "yyyy-MM-dd";

    public final static String ADMIN = "ADMIN";

    public final static class STATUS {
        public final static String INACTIVE = "0";
        public final static String ACTIVE = "1";
        public final static String CANCELED = "2";
    }

    public final static class STATUS_RESPONSE {
        public final static String SUCCESS = "success";
        public final static String FAIL = "fail";
    }

    public final static class AREA_CODE_LENGTH {
        public final static Integer PROVINCE = 4;
        public final static Integer DISTRICT = 7;
        public final static Integer PRECINCT = 10;
    }

    public final static class PARTNER_STATUS {
        public String ACTIVE = "1";
    }

    public final static String DOCUMENT_CODE_PREFIX = "DOC-";
    public final static char PAD_CHAR = '0';

    public final static class DOC_OBJ_MAP_TYPE {
        public final static String PRODUCT = "PRODUCT";
        public final static String PARTNER = "PARTNER";
        public final static String PRODUCT_OFFER = "PRODUCT_OFFER";
        public final static String PARTNER_REVENUE_SHARE = "PARTNER_REVENUE_SHARE";
        public final static String CUSTOMER = "CUSTOMER";
        public final static String SUBSCRIBER = "SUBSCRIBER";
    }

    public final static class GLOBAL_LIST_CODE {
        public final static String PARTNER_STATUS = "PARTNER_STATUS";
        public final static String PARTNER_SHARE_TYPE = "PARTNER_SHARE_TYPE";
        public final static String PRODUCT_GROUP = "PRODUCT_GROUP";
        public final static String PRODUCT_TYPE = "PRODUCT_TYPE";
        public final static String CUSTOMER_TYPE = "CUSTOMER_TYPE";
        public final static String CUSTOMER_BUS_TYPE = "CUSTOMER_BUS_TYPE";
        public final static String ID_TYPE = "ID_TYPE";
        public final static String PRODUCT_STATUS = "PRODUCT_STATUS";
        public final static String PRODUCT_OFFER_STATUS = "PRODUCT_OFFER_STATUS";
        public final static String VOL_LIMITATION_TYPE = "VOL_LIMITATION_TYPE";
        public final static String PRODUCT_OFFER_TYPE = "PRODUCT_OFFER.PRODUCT_OFFER_TYPE";
        public final static String DOCUMENT_STATUS = "DOCUMENT_STATUS";
        public final static String VAT_TYPE = "VAT_TYPE";
        public final static String SEX = "SEX";
        public final static String ORDER_TYPE = "ORDER_TYPE";
        public final static String ORDER_ACTION_TYPE = "ORDER_ACTION_TYPE";
        public final static String ORDER_STATUS = "ORDER_STATUS";
        public final static String SALE_CHANNEL = "SALE_CHANEL";
        public final static String BILL_NOTIFICATION_METHOD = "BILL_NOTIFICATION_METHOD";
        public final static String DOCUMENT_TYPE = "DOCUMENT_TYPE";
        public final static String PRODUCT_CUST_SEGM = "PRODUCT_CUST_SEGM";
        public final static String PROVISION_TYPE = "PROVISION_TYPE";
        public final static String CUSTOMER_EXT_MEDICAL_MEDICAL_ORG_TYPE = "CUSTOMER_EXT_MEDICAL.MEDICAL_ORG_TYPE";
    }

    public static class ORDER_TYPE {
        public final static String CONNECT = "1";
        public final static String AFTER_SALE = "2";
        public final static String CONTRACT = "3";
    }

    public static class CUSTOMER_BUS_TYPE {
        public final static String PERSONAL = "1";
        public final static String BUSINESS = "2";
    }

    public static class PROVISIONING_STATUS {
        public final static String WAIT_FOR_CONNECT = "0";
        public final static String CONNECTED = "1";
    }

    public static class ORDER_STATUS {
        public final static String PENDING = "1";
        public final static String EXECUTED = "2";
    }

    public static class ORDER_ACTION_TYPE {
        public final static String CONNECT_WAIT_SURVEY = "1";               //Đấu nối - Chờ khảo sát
        public final static String CONNECT_WAIT_DEPLOY = "2";               //Đấu nối - Chờ triển khai
        public final static String CONNECT_WAIT_ACCEPTANCE = "3";           //Đấu nối - Chờ nghiệm thu
        public final static String CONNECT_WAIT_SYNC_BCCS = "4";            //Đấu nối - Chờ đồng bộ BCCS
        public final static String CONNECT_ACCEPTANCED = "5";               //Đấu nối - Đã nghiệm thu
        public final static String CONNECT_CANCEL = "6";                    //Đấu nối - Hủy yêu cầu
        public final static String AFTER_SALE_CHANGE_INFOR = "7";           //Sau bán - Thay đổi thông tin
        public final static String AFTER_SALE_CHANGE_PRODUCT_OFFER = "8";   //Sau bán - Thay đổi gói cước
        public final static String AFTER_SALE_EXTEND_PRODUCT_OFFER = "9";   //Sau bán - Gia hạn gói cước
        public final static String CONTRACT_CHANGE_INFOR_KHYC = "10";       //Hơp đồng - Thay đổi thông tin - KHYC
        public final static String CONTRACT_CHANGE_INFOR_CORRECT = "11";    //Hơp đồng - Thay đổi thông tin - Sửa sai
        public final static String CONTRACT_TRANSFER = "12";                //Hơp đồng chuyển nhượng
        public final static String CONTRACT_LIQUIDATION = "13";             //Hợp đồng - Thanh lý hợp đồng
        public final static String CONTRACT_EXTEND = "14";                  //Hợp đồng - Gia hạn hợp đồng
    }

    public static class SUBSCRIBER_STATUS {
        public final static String DONT_CONNECT = "1";                      //Chưa đấu nối
        public final static String CONNECT_WAIT_ACCEPTANCE = "7";           //Chờ nghiệm thu
        public final static String ACTIVE = "2";                            //Đang hoạt động
        public final static String CANCELED = "3";                          //Đã bị hủy
    }

    public static class PRODUCT_OFFER_TYPE {
        public final static String ACCORDING_PRICE_BRACKET = "1";           //Theo khung giá
        public final static String NO_PRICE_DETERMINATION = "2";            //Không xác định mức giá
        public final static String FIX_PRICE = "3";                         //Giá cố định
    }

    public static String SUBSCRIBER_ACCOUNT_NO_FORMAT = "SUBSCRIBER.ACCOUNT_NO.FORMAT";
    public static String SUBSCRIBER_APP_ACCOUNT_NO_FORMAT = "SUBSCRIBER.APP_ACCOUNT_NO.FORMAT";

    public static final String BEGIN_LOG = " -- BEGIN -----------";
    public static final String END_LOG = " -- END ---------------";
    public static final String PARAMETER = " -PARAM INPUT -";
    public static final String ERROR_LOG = "ERROR:";

    public static final String TEMPLATE_TYPE = ".jrxml";


    public static final class REPORT_TEMPLATE_CODE {
        public static final String REPORT_NEW_CONNECT_MEDICAL_CODE = "RP_NEW_CONNECT_MEDICAL";
        public static final String REPORT_NEW_CONNECT_MEDICAL_NAME = "report_new_connect_medical_v1.1.jrxml";
    }
}
