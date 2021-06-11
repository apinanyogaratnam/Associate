package com.apinanyogaratnam;

import java.util.Locale;

public class Utils {
    public static String removeStartEndChars(String string) {
        try {
            return string.substring(1, string.length() - 1);
        } catch (Exception e) {
            Print.print("Cannot remove {} on empty string.");
        }

        return "";
    } // tested

    public static String [] splitCommas(String string) {
        return string.split(",");
    } // tested

    public static String [] indexList(String string) {
        return splitCommas(removeStartEndChars(string));
    } // tested

    public static String parseString(String string) {
        String newString = "";
        for (int i=0; i<string.length(); i++) {
            Character c = string.charAt(i);
            if (c.equals("'")) {
                newString += "''";
                continue;
            }
            newString += c;
        }

        return newString;
     }
}
