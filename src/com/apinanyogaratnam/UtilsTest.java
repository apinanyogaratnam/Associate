package com.apinanyogaratnam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void removeStartEndCharsTest() {
        String string = "{mcdonalds, tim hortons, nike, adidas}";
        String parsedString = Utils.removeStartEndChars(string);

        // testing possible sql text
        assertEquals("mcdonalds, tim hortons, nike, adidas", parsedString);

        // testing empty string test
        string = "";
        string = Utils.removeStartEndChars(string);
        assertEquals("", string);
    }

    @Test
    void splitCommasTest() {
        String string = "apinan,angel,stewie,bubs,apinu,api,AP";
        String [] array = Utils.splitCommas(string);

        // checking each string is indexed properly
        assertEquals("apinan", array[0]);
        assertEquals("angel", array[1]);
        assertEquals("stewie", array[2]);
        assertEquals("bubs", array[3]);
        assertEquals("apinu", array[4]);
        assertEquals("api", array[5]);
        assertEquals("AP", array[6]);

        string = "name";
        String [] singleArray = Utils.splitCommas(string);

        // checking if splitCommas splits a string without commas
        assertEquals(1, singleArray.length);

        string = ",";
        String [] emptyStrings = Utils.splitCommas(string);

        // checking if split also includes empty string
        assertEquals("", emptyStrings[0]);
        assertEquals("", emptyStrings[1]);
        assertEquals(2, emptyStrings.length);

    }

    @Test
    void indexListTest() {
        String string = "{mcdonalds, tim hortons, nike, adidas}";
        String [] indexed = Utils.indexList(string);

        // checking if indexed gives correct elements
        assertEquals("mcdonalds", indexed[0]);
        assertEquals("tim hortons", indexed[1]);
        assertEquals("nike", indexed[2]);
        assertEquals("adidas", indexed[3]);
    }

    @Test
    void parseStringTest() {
        String string = "McDonald's";
        string = Utils.parseString(string);

        // checking if the extra ' is added
        assertEquals("McDonald''s", string);
    }
}