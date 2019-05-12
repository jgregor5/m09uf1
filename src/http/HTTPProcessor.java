package http;

/**
 *
 * @author julian
 */
public interface HTTPProcessor {
    
    void process(HTTPRequest req, HTTPResponse res);
}
