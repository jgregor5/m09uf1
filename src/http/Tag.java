package http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Fluent interface for xml building
 * @author julian
 */
public class Tag {
    
    private static final String[] UNSHORTED = 
            new String[]{"div", "i", "textarea", "span", "legend"};
    private static final String[] ADDSPACE = new String[]{"div"};

    private String name;
    private Map<String, String> atts;
    private List<Object> body;

    public Tag() {
        this(null);
    }

    public Tag(String name) {
        this.name = name;
        this.atts = new LinkedHashMap<>();
        this.body = new ArrayList<>();
    }

    public String get(String name) {
        return this.atts.get(name);
    }

    public Tag set(String name, String value) {
        return set(name, value, false);
    }

    public Tag set(String name, String value, boolean allowEmptyValue) {
        if (value != null && (value.length() > 0 || allowEmptyValue)) {
            String curr = this.atts.get(name);
            if (curr != null) {
                value = curr + " " + value;
            }
            this.atts.put(name, value);
        }
        return this;
    }

    public Tag blank(String name) {
        this.atts.put(name, "");
        return this;
    }

    public Tag add(String text) {
        if (text != null && text.length() > 0) {
            this.body.add(text);
        }
        return this;
    }

    public Tag add(Tag tag) {
        if (tag != null) {
            this.body.add(tag);
        }
        return this;
    }

    public Tag add(Tag... tags) {
        for (Tag tag : tags) {
            if (tag != null) {
                this.body.add(tag);
            }
        }
        return this;
    }

    public Tag addLine() {
        this.body.add("\n");
        return this;
    }

    private boolean unshorted(String tag) {
        return Arrays.asList(UNSHORTED).contains(tag);
    }

    private boolean addspace(String tag) {
        return Arrays.asList(ADDSPACE).contains(tag);
    }

    public boolean isEmpty() {
        return body.isEmpty();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        // tag start
        if (name != null) {    
            StringBuilder asb = new StringBuilder();
            if (atts.size() > 0) {
                for (Entry<String, String> entry : atts.entrySet()) {
                    asb.append(" ");
                    asb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                }
                asb.append(" ");
            }            
            
            if (body.size() > 0 || unshorted(name)) {
                sb.append("<").append(name).append(asb.toString()).append(">");
            } else { // no body and tag can be shorted
                sb.append("<").append(name).append(asb.toString()).append("/>");
            }
        }

        // body        
        int bodyCount = 0;
        for (Object value : body) {
            if (value != null) {
                sb.append(value.toString());
                bodyCount += value.toString().length();
            }
        }
        
        // some tags need a space after the body
        if (bodyCount == 0 && addspace(name)) {
            sb.append("&nbsp;");
        }

        // tag end        
        if (name != null) {
            if (body.size() > 0 || unshorted(name)) {
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }
}
