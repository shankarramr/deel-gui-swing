package com.deel.test;

import java.util.Calendar;

/**
 *
 * @author neo
 */
public class DateTimeTest {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        System.out.format("%td/%tm/%tY %tH:%tM", c, c, c, c, c);
    }
}