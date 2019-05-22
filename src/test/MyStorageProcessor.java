package test;

import http.GenericStorage;
import http.HTTPProcessor;
import http.HTTPRequest;
import http.HTTPResponse;
import http.HTTPUtils;
import http.SessionStorage;
import http.SocketHTTPServer;
import http.Tag;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author julian
 */
public class MyStorageProcessor implements HTTPProcessor {

    @Override
    public void process(HTTPRequest req, HTTPResponse res) {

        Tag html = new Tag("html");
        Tag body = new Tag("body");
        html.add(body);

        GenericStorage gs = new GenericStorage();
        Number ng = gs.getNumber("gs_counter");
        ng = ng == null? 1 : ng.intValue() + 1;
        gs.set("gs_counter", ng);
        
        body.add(new Tag("h1").add("Storage test"));
        body.add(new Tag("h2").add("generic counter: " + ng));

        Map<String, String> params = HTTPUtils.parseParameters(req.getQueryString());  
        
        if (params.containsKey("key")) {
            String key = params.get("key");
            SessionStorage ss = SessionStorage.getInstance(key);
            if (ss == null) {
                ss = SessionStorage.newInstance(key);
            }
            
            Number ns = ss.getNumber("ss_counter");
            ns = ns == null? 1 : ns.intValue() + 1;
            ss.set("ss_counter", ns);
            
            body.add(new Tag("h2").add("session counter: " + ns));
        }
        
        // WRITE
        
        PrintWriter out = res.getWriter();
        String htmlStr = html.toString();
        
        out.println("HTTP/1.1 200 Ok");
        out.println("Server: jgregor5");
        out.println("Date: " + new Date());
        out.println("Content-type: text/html; charset=utf-8");
        out.println("Content-length: " + htmlStr.getBytes().length);
        out.println(); // blank line!
        out.println(htmlStr);
        out.flush();        
    }
    
    public static void main(String[] args) {                
        SocketHTTPServer.start(MyStorageProcessor::new, 8080, false);
    }    
}
