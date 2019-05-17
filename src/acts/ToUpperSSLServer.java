package acts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author julian
 */
public class ToUpperSSLServer {
    
    static final int PORT = 6666;

    public static void main(String[] args) throws IOException {
        
        System.setProperty("javax.net.ssl.keyStore", 
                CryptoUtils.getAbsoluteFilename("ks/serverKeystore.jks"));
        System.setProperty("javax.net.ssl.keyStorePassword", "yourpassword");
        
        System.setProperty("javax.net.ssl.trustStore", 
                CryptoUtils.getAbsoluteFilename("ks/serverTruststore.jks"));
        System.setProperty("javax.net.ssl.trustStorePassword", "yourpassword");

        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();        
        SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);
        
        serverSocket.setNeedClientAuth(true);
        
        Socket clientSocket = serverSocket.accept();
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        out.println("hola!");
        
        String text;
        while ((text = in.readLine()).length() > 0)        
            out.println(text.toUpperCase());

        out.println("adeu!");

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
