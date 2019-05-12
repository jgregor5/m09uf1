package acts;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;
import http.HTTPProcessor;
import http.HTTPRequest;
import http.HTTPResponse;
import http.SocketHTTPServer;
import http.Tag;

/**
 *
 * @author julian
 */
public class MyHTTPProcessor implements HTTPProcessor {

    @Override
    public void process(HTTPRequest req, HTTPResponse res) {
        defaultProcess(req, res);
    }
    
    private void defaultProcess(HTTPRequest req, HTTPResponse res) {
        
        // BUILD HTML
        
        Tag bodyTag = new Tag("body");
        Tag htmlTag = new Tag("html").add(bodyTag);
        
        String h1Str = req.getMethod() + " " + req.getRequestURI() + "?" + req.getQueryString();
        bodyTag.add(new Tag("h1").add(h1Str));
        
        Tag ulTag = new Tag("ul");
        Set<String> names = req.getHeaderNames();
        for (String name: names) {
            ulTag.add(new Tag("li").add(name + ": " + req.getHeader(name)));
        }
        bodyTag.add(ulTag);
        
        String bodyStr = req.getBody();
        if (bodyStr != null && bodyStr.length() > 0) {
            bodyTag.add(new Tag("h2").add("body"));
            bodyTag.add(new Tag("p").add(bodyStr));
        }
        
        // WRITE
        
        PrintWriter out = res.getWriter();
        String html = htmlTag.toString();
        
        out.println("HTTP/1.1 200 Ok");
        out.println("Server: jgregor5");
        out.println("Date: " + new Date());
        out.println("Content-type: text/html; charset=utf-8");
        out.println("Content-length: " + html.getBytes().length);
        out.println(); // blank line!
        out.println(html);
        out.flush();
    }    

    public static void main(String[] args) {                
        SocketHTTPServer.start(MyHTTPProcessor::new, 8080, false);
    }
}
