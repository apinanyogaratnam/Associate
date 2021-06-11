package com.apinanyogaratnam;

import java.util.Locale;

public class Utils {
    public static String removeStartEndChars(String string) {
        try {
            return string.substring(1, string.length() - 1);
        } catch (Exception e) {
            Print.print("Cannot remove start and end chars on empty string.");
        }

        return "";
    } // tested

    public static String removeEndChar(String string) {
        try {
            return string.substring(0, string.length()-1);
        } catch (Exception e) {
            Print.print("Cannot remove end char on empty string");
        }

        return "";
    }

    public static String [] splitCommas(String string) {
        return string.split(",");
    } // tested

    public static String [] indexList(String string) {
        return splitCommas(removeStartEndChars(string));
    } // tested

    public static String parseString(String string) {
        if (!string.contains("'")) return string;

        String newString = "";
        for (int i=0; i<string.length(); i++) {
            Character c = string.charAt(i);
            if (c.equals('\'')) {
                newString += "''";
                continue;
            }
            newString += c;
        }

        return newString;
     } // tested

    public static String unParseString(String string) {
        if (!string.contains("''")) return string;

        StringBuilder newString = new StringBuilder();
        newString.append(string);

        int index = string.indexOf("''");
        while (index != -1) {
            newString.delete(index, index+1);

            index = string.indexOf("''", index+1);
        }

        return newString.toString();
    } // tested
}
