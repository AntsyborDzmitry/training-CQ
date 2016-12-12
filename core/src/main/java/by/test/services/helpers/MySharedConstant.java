package by.test.services.helpers;


public interface MySharedConstant {
    String PROP_NAME = "cq:myPropName";
    String PROP_DESCR = "cq:myPropDescr";
    String EVENT_TOPIC = "it/can/be/any/string";


    /*example for array and Map
    Locale[] ST_CONTENT_LOCALES = {Locale.ENGLISH, Locale.CHINESE, Locale.JAPANESE};

    Map<Locale, String> SOLR_LOCALE_LANG_MAPPING = new LinkedHashMap<Locale, String>() {{
        put(Locale.ENGLISH, "en");
        put(Locale.CHINESE, "cn");
        put(Locale.JAPANESE, "jp");
    }};*/
}
