package com.apinanyogaratnam;

public class Utils {
    public static String removeStartEndChars(String string) {
        // try removing start and end chars
        try {
            return string.substring(1, string.length() - 1);
        } catch (Exception e) {
            Print.print("Cannot remove start and end chars on empty string.");
        }

        return "";
    }

    public static String removeEndChar(String string) {
        // try removing end char
        try {
            return string.substring(0, string.length()-1);
        } catch (Exception e) {
            Print.print("Cannot remove end char on empty string");
        }

        return "";
    }

    public static String [] splitCommas(String string) {
        return string.split(",");
    }

    public static String [] indexList(String string) {
        return splitCommas(removeStartEndChars(string));
    }

    public static String parseString(String string) {
        if (!string.contains("'")) return string;

        String newString = "";
        for (int i=0; i<string.length(); i++) {
            Character c = string.charAt(i);

            // if out of bounds, add last string and return
            try {
                Character d = string.charAt(i + 1);
                if (c.equals('\'') && !d.equals('\'')) {
                    // single quotes ex. McDonald's
                    newString += "''";
                    continue;
                } else if (c.equals('\'') && d.equals('\'')) {
                    // double quotes ex. McDonald''s
                    newString += "'";
                    i++;
                }
                newString += c;
            } catch (Exception e) {
                // out of bounds error occurred
                newString += c;
                break;
            }
        }

        return newString;
     }

    public static String unParseString(String string) {
        if (!string.contains("''")) return string;

        // creating a string builder object
        StringBuilder newString = new StringBuilder();

        // adding string to object
        newString.append(string);

        // getting index of double quotes
        int index = string.indexOf("''");
        while (index != -1) {
            // removing a quote from double quotes
            newString.delete(index, index+1);

            // updating index variable with new index to not cause an infinite loop
            index = string.indexOf("''", index+1);
        }

        return newString.toString();
    }
}
