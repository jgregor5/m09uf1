package http;

import java.io.File;

/**
 *
 * @author julian
 */
public class GenericStorage extends AbstractStorage {

    static Object monitor = new Object();

    @Override
    File getStorageFile() {
        File folder = new File(System.getProperty("user.dir"), "data");
        if (!folder.exists()) {
             folder.mkdir();
        }
        
        return new File(folder, "generic.json");
    }

    @Override
    Object getMonitor() {
        return monitor;
    }
    
}
