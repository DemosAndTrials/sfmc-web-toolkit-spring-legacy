package sfdc.mc.util;

/**
 * Helpers
 */
public class Helpers {

    /**
     * Remove the last character from a string
     * @param str
     * @return
     */
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

}
