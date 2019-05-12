package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author julian
 */
public abstract class AbstractStorage {
    
    // GET / SET
    
    public void set(String property, Object value) {
        
        synchronized (getMonitor()) {
            
            if (value == null) {
                value = JSONObject.NULL;
            } 

            JSONObject data = load();
            data.put(property, value);
            save(data);
            
        }        
    }
    
    public Object get(String property) {
    
        synchronized (getMonitor()) {
            JSONObject data = load();
            return data.has(property)? data.get(property) : null;    
        }
    }
    
    public String getString(String property) {

        synchronized (getMonitor()) {
            JSONObject data = load();
            return data.has(property)? data.getString(property) : null;
        }
    }
    
    public Number getNumber(String property) {

        synchronized (getMonitor()) {
            JSONObject data = load();
            return data.has(property)? data.getNumber(property) : null;
        }
    }
    
    public Boolean getBoolean(String property) {

        synchronized (getMonitor()) {
            JSONObject data = load();
            return data.has(property)? data.getBoolean(property) : null;
        }
    }
    
    // PRIVATE UTILS
    
    abstract File getStorageFile();
    abstract Object getMonitor();
    
    private void save(JSONObject data) {
        
        try (FileWriter fw = new FileWriter(getStorageFile())) {
            fw.write(data.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private JSONObject load() {
        
        try (BufferedReader br = new BufferedReader(new FileReader(getStorageFile()))) {            
            return new JSONObject(br.readLine());
        
        } catch (FileNotFoundException ex) {
            return new JSONObject();
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
