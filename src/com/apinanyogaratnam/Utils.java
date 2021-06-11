package com.apinanyogaratnam;

public class Utils {
    public static String removeStartEndChars(String string) {
        try {
            return string.substring(1, string.length() - 1);
        } catch (Exception e) {
            Print.print("Cannot remove {} on empty string.");
        }

        return "";
    }

    public static String [] splitCommas(String string) {
        return string.split(",");
    }

    public static String [] indexList(String string) {
        return splitCommas(removeStartEndChars(string));
    }
}
