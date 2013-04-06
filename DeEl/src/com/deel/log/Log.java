package com.deel.log;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class Log {
    public static final String ERROR_LOG_FILE_PATH = "res/log/log.dat";
    private static final String ERROR = "ERROR";
    private static final String DELIMITER_SYMBOL1 = "@";
    private static final String DELIMITER_SYMBOL2 = "#";
    private static final String FILE_NEW_LINE = "\r\n";
    
    public static void e(Exception exception, boolean writeToLogFile) {
        StringBuilder exceptionStackTrace = new StringBuilder();
        for(int i = 0; i < exception.getStackTrace().length; i++) {
                    exceptionStackTrace.append(exception.getStackTrace()[i]);
        }
        if(writeToLogFile) {
            try {
                boolean isNewFile = false;
                File f = new File(ERROR_LOG_FILE_PATH);
                if(!f.exists()) {
                    isNewFile = true;
                }
                FileWriter fw = new FileWriter(f, true);
                if(isNewFile){
                    String logHeader = "DeEl Error Log File" +
                                       DELIMITER_SYMBOL1 +
                                       "Application Generated" +
                                       DELIMITER_SYMBOL2 +
                                       "Please do not modify or delete" +
                                       FILE_NEW_LINE + FILE_NEW_LINE;
                    fw.write(logHeader);
                }                                
                String entry = DELIMITER_SYMBOL1 +
                               getCurrentTimeStamp() + 
                               DELIMITER_SYMBOL2 +
                               ERROR +
                               DELIMITER_SYMBOL2 +
                               exception.getClass() +
                               DELIMITER_SYMBOL2 +
                               exception.getMessage() +
                               DELIMITER_SYMBOL2 +
                               exceptionStackTrace+
                               FILE_NEW_LINE + FILE_NEW_LINE;
                fw.write(entry);
                fw.close();
            } catch(Exception ex) {ex.printStackTrace();}
        } else {
            System.err.println("@" + exception.getClass() + "#" + exception.getMessage() + "#" + exceptionStackTrace);
        }
    }
    
    private static String getCurrentTimeStamp() {
        Calendar c = Calendar.getInstance();
        return String.format("%td/%tm/%tY %tH:%tM", c, c, c, c, c);
    }
}