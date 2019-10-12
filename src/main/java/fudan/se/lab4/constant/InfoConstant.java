package fudan.se.lab4.constant;

import java.util.Locale;
import java.util.ResourceBundle;

public class InfoConstant {
    private static ResourceBundle res = ResourceBundle.getBundle(InfoConstant.class.getSimpleName(), Locale.US);
    private static ResourceBundle err = ResourceBundle.getBundle(InfoConstant.class.getSimpleName(), Locale.US);

    /**
     * This function is used to set res of this class
     *
     * @param locale the locale to be used to set res
     */
    public static void setResourceBundle(Locale locale) {
        res = ResourceBundle.getBundle(InfoConstant.class.getSimpleName(), locale);
    }

    /**
     * This function is used to get info constant in properties by key
     *
     * @param key the key to the value want to get
     * @return the value to the key
     */
    public static String getInfo(String key) {
        return res.getString(key);
    }

    /**
     * This function is used to get log infos, always in English
     * @param key to the value want to get
     * @return the value to the key
     */
    public static String getLogInfo(String key) {
        return err.getString(key);
    }
}
