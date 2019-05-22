package http;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author julian
 */
public class SessionStorage extends AbstractStorage {
    
    private static Map<String, SessionStorage> sessions;
    
    public synchronized static void init() {
        File folder = new File(System.getProperty("user.dir"), "sessions");
        for (File file: folder.listFiles()) {
            file.delete();
        }
    }
    
    public synchronized static SessionStorage getInstance(String key) {
        
        if (sessions == null) {
            sessions = new HashMap<>();
        }
        
        return sessions.get(key);
    }
    
    public synchronized static SessionStorage newInstance() {
        
        String key = UUID.randomUUID().toString();        
        return newInstance(key);
    }
    
    public synchronized static SessionStorage newInstance(String key) {
        
        if (sessions == null) {
            sessions = new HashMap<>();
        }
      
        SessionStorage session = new SessionStorage(key);
        sessions.put(key, session);
        return session;
    }
    
    public synchronized static boolean removeInstance(String key) {
        
        if (sessions != null) {
            return sessions.remove(key) != null;
        }
        else {
            return false;
        }
    }
    
    // CONSTRUCTOR (private)

    private final String key;    
    
    private SessionStorage(String key) {
        this.key = key;
    }
        
    public String getKey() {
        return this.key;
    }    

    @Override
    File getStorageFile() {
        File folder = new File(System.getProperty("user.dir"), "sessions");
        if (!folder.exists()) {
             folder.mkdir();
        }
        
        return new File(folder, key + ".json");
    }
    
    @Override
    Object getMonitor() {
        return this.key;
    }
            
    public boolean delete() {        
        File file = getStorageFile(); 
        boolean deleted = !file.exists() || file.delete();
        removeInstance(key);
        return deleted;
    }

}
