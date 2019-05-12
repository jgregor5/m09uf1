package http;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author julian
 */
public class HTTPResponse {

    private final PrintWriter out;
    private final BufferedOutputStream dataOut;
    private final Map<String, String> headers;
 
    HTTPResponse(PrintWriter out, BufferedOutputStream dataOut) {
        this.out = out;
        this.dataOut = dataOut;
        this.headers = new HashMap<>();
    }
    
    public String getHeader(String name) { 
        return this.headers.get(name);
    }
    
    public void setHeader(String name, String value) {        
        this.headers.put(name, value);
    }
    
    public PrintWriter getWriter() {        
        return out;
    }
    
    public BufferedOutputStream getOutputStream() {
        return this.dataOut;
    }
}
