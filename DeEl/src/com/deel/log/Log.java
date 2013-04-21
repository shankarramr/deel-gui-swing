package com.deel.log;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

/**
 *
 * @author neo
 */
public class Log {
    private static final String LOG_FILE_LOCATION = "src/log.dat";
    private static final String ERROR = "ERROR";
    private static final String DELIMITER_SYMBOL = "#";
    private static final String FILE_NEW_LINE = "\r\n";
    
    public static void e(String exceptionClass, String exceptionMessage) {
        try {
            FileWriter fw = new FileWriter(new File(LOG_FILE_LOCATION), true);
            String entry = getCurrentTimeStamp() + 
                           DELIMITER_SYMBOL + 
                           ERROR + 
                           DELIMITER_SYMBOL + 
                           exceptionClass + 
                           DELIMITER_SYMBOL + 
                           exceptionMessage +
                           FILE_NEW_LINE;
            fw.write(entry);
            fw.close();
        } catch(Exception ex) {ex.printStackTrace();}
    }
    
    private static String getCurrentTimeStamp() {
        Calendar c = Calendar.getInstance();
        return String.format("%td/%tm/%tY %tH:%tM", c, c, c, c, c);
    }
}