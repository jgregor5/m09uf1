package http;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author julian
 */
public class HTTPRequest {

    private final String method, uri, body, query;
    private final Map<String, String> headers;
    
    HTTPRequest(String method, String uri, Map<String, String> headers, String body) {
        this.method = method;
        this.headers = headers;
        this.body = body;
        
        int idx = uri != null? uri.indexOf('?') : -1;        
        if (idx != -1) {
            this.uri = uri.substring(0, idx);
            if (idx + 1 < uri.length()) {
                this.query = uri.substring(idx + 1);
            }
            else {
                this.query = null;
            }
        }
        else {
            this.uri = uri;
            this.query = null;
        }
    }
        
    public String getMethod() {
        return this.method;
    }
    
    public String getRequestURI() {
        return this.uri;
    }
    
    public String getQueryString() {
        return this.query;
    }

    public String getHeader(String name) {   
        return this.headers.get(name);
    }
    
    public Set<String> getHeaderNames() {
        return new HashSet<>(this.headers.keySet());
    }
    
    public String getBody() {
        return this.body;
    }
}
