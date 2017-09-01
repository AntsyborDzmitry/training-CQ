package by.test.services.helpers;


public interface MySharedConstant {
    String PROP_NAME = "cq:myPropName";
    String PROP_DESCR = "cq:myPropDescr";
    String EVENT_TOPIC = "it/can/be/any/string";

    String SLASH = "/";
    String DOUBLE_SLASH = "//";
    String DOT = ".";
    String DASH = "-";
    String UNDERSCORE = "_";
    String OPEN_PARENTHESIS = "(";
    String CLOSE_PARENTHESIS = ")";
    String EMPTY_LINK = "#";
    String PATH_ETC_LMU = "/etc/lmu";
    String PATH_ETC_LMU_ORDER = PATH_ETC_LMU + "/orders";
    String PATH_ETC_LMU_ERROR = PATH_ETC_LMU + "/error";
    String FIRST_NAME = "firstName";
    String LAST_NAME = "lastName";
    String GIVEN_NAME = "givenName";
    String FAMILY_NAME = "familyName";
    String PATH_ETC_LMU_CONFIG = PATH_ETC_LMU + "/config";
    String PATH_ETC_LMU_CONFIG_PROPERTIES = PATH_ETC_LMU_CONFIG + "/properties";
    String PATH_WEBROOT_ORDER_CREATE_WSDL = "file:/opt/aem/EstoreOrderDetailsImplService.wsdl";
    String PATH_WEBROOT_ORDER_UPDATE_WSDL = "file:/opt/aem/EstoreOrderStatusUpdateImplService.wsdl";
    String WS_AUTH_USERNAME = "WS_AUTH_USERNAME";
    String WS_AUTH_PASSWORD = "WS_AUTH_PASSWORD";
    String CREATE_ORDER_ENDPOINT = "CREATE_ORDER_ENDPOINT";
    String USER_PROFILE_ORDERS_APPROVED = "ordersApproved";
    String USER_PROFILE_ORDERS_REJECTED = "ordersRejected";
    String PROPERTY_LMU_STORE_PATH = "lmuStorePath";
    String KEY_TIMESTAMP = "timestamp";
    String KEY_DL_LINK = "dlLink";
    String KEY_PRMIS_ID = "prmisId";
    String KEY_USER_PROFILE_PATH = "userProfilePath";
    String KEY_TO_SUCCESS = "success";
    String KEY_RESULT = "result";
    String PROPERTY_ID = "id";
    String PROPERTY_USER_PATH = "userPath";
    String PROPERTY_LOCALE = "locale";
    String PROPERTY_BLACK_LIST = "blackList";
    String PROPERTY_PATH = "path";
    String PROPERTY_LOCALIZED = "localized";
    String PROPERTY_CPN_ID = "cpnId";
    String PN_SW_RELEASE = "softwareRelease";
    String PROPERTY_RPN_ID = "rpnId";
    String PROPERTY_RPN_NAME = "rpnName";
    String PROPERTY_INPUT = "input";
    String PROPERTY_INFORM = "inform";
    String PROPERTY_CURRENT_PATH = "currentPath";
    String KEY_EXT_PATH = "extPath";
    String PROPERTY_USER_EMAIL = "userEmail";
    String PROPERTY_EMAIL = "email";
    String PROP_VALUE_NOT_APPLICABLE = "NA";
    String TEXT_PARAMETER = "text";
    String VERSION_PARAMETER = "version";
    String VERSION_DATE_PARAMETER = "versionDate";
    String PRODUCT_ID_PARAMETER = "productId";
    String LICENSE_ACCEPT_TIMESTAMP = "acceptTimestamp";
    String PROPERTY_SOFTWARE_DL_PATH = "softwareDlPath";

    String PROPERTY_PRODUCT_NAME = "productName";
    String LMU_ORDER_STATUS_PENDING = "ZPEN";
    String LMU_ORDER_STATUS_ACCEPTED = "ZACC";
    String LMU_ORDER_STATUS_REJECTED = "ZREJ";
    String LMU_ORDER_STATUS_UNKNOWN = "UNKNOWN";
    String EMAIL_RECIPIENT_NAME = "recipientName";
    String EMAIL_SOFTWARE_LINK = "downloadSoftwareLink";

    /**
     * Constant for author run mode
     */
    String AUTHOR_RUN_MODE = "author";
    String STATUS_OK = "OK";


    /*example for array and Map
    Locale[] ST_CONTENT_LOCALES = {Locale.ENGLISH, Locale.CHINESE, Locale.JAPANESE};

    Map<Locale, String> SOLR_LOCALE_LANG_MAPPING = new LinkedHashMap<Locale, String>() {{
        put(Locale.ENGLISH, "en");
        put(Locale.CHINESE, "cn");
        put(Locale.JAPANESE, "jp");
    }};*/
}
