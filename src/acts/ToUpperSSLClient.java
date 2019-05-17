package acts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author julian
 */
public class ToUpperSSLClient {
    
    static final int PORT = 6666;
    static final String HOST = "localhost";
 
    public static void main(String[] args) throws IOException {
        
        System.setProperty("javax.net.ssl.keyStore", 
                CryptoUtils.getAbsoluteFilename("ks/clientKeystore.jks"));
        System.setProperty("javax.net.ssl.keyStorePassword", "yourpassword");
        
        System.setProperty("javax.net.ssl.trustStore", 
                CryptoUtils.getAbsoluteFilename("ks/clientTruststore.jks"));
        System.setProperty("javax.net.ssl.trustStorePassword", "yourpassword");
        
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket clientSocket = (SSLSocket) factory.createSocket(HOST, PORT);    
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        
        String salutacio = in.readLine();
        System.out.println("salutacio: " + salutacio);
        
        for (String text: new String[]{"u", "dos", "tres"}) {
            out.println(text);
            String resposta = in.readLine();
            System.out.println(text + " => " + resposta);
        }
        
        out.println();
        
        String comiat = in.readLine();
        System.out.println("comiat: " + comiat);
        
        in.close();
        out.close();
        clientSocket.close();
    }
}
