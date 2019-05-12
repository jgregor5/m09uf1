package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author julian
 */
public class HTTPUtils {
    
    private HTTPUtils() {}
    
    public static Map<String, String> parseCookies(String cookieHeaderValue) {
        
        Map<String, String> result = new HashMap<>();
        if (cookieHeaderValue == null) {
            return result;
        }
        
        String[] sets = cookieHeaderValue.split(";");        
        for (String set: sets) {
            String[] nameValue = set.split("=");
            String name = nameValue[0].trim();
            String value = null;
            if (nameValue.length == 2) {
                value = nameValue[1].trim();
            }
            result.put(name, value);
        }
        
        return result;
    }
    
    public static Map<String, String> parseParameters(String query) {
        
        Map<String, String> result = new HashMap<>();
        if (query == null) {
            return result;
        }
        
        String[] pairs = query.split("&");                        
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            
            try {
                if (idx == -1) {
                    String key = URLDecoder.decode(pair, "UTF-8");
                    result.put(key, "");
                }
                else {
                    String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");                                
                    result.put(key, value);                
                }
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }            
        }  
        
        return result;
    }
    
    public static String toCookieDate(Date date) {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        DateFormat fmt = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US);
        fmt.setTimeZone(tz);
        return fmt.format(date);
    }
    
    public static byte[] readFileBytes(File file, int fileLength) throws IOException {
        
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }

        return fileData;
    }
}
