package http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author julian
 */
public class SocketHTTPServer {
    
    static final int NFILS = 10;
    
    private static ExecutorService executor;

    private Socket socket;
    private HTTPProcessorFactory factory;
    
    public SocketHTTPServer(Socket socket, HTTPProcessorFactory factory) {
       this.socket = socket;
       this.factory = factory;
    }
    
    public void handle() {

        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        Map<String, String> headers = new HashMap<>();

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            dataOut = new BufferedOutputStream(socket.getOutputStream());

            // first line of response
            String input = in.readLine();
            if (input == null) {
                throw new RuntimeException("request line is missing");
            }
            
            String[] parts = input.split(" ");
            String method = parts[0].toUpperCase();
            String uri = parts[1].toLowerCase();
            
            while ((input = in.readLine()).length() > 0) {
                int idx = input.indexOf(':');
                headers.put(input.substring(0, idx).trim(), input.substring(idx+1).trim());
            }
            
            StringBuilder body = new StringBuilder();
            while (in.ready()) {
                body.append((char) in.read());
            }
            
            HTTPRequest req = new HTTPRequest(method, uri, headers, body.toString());
            HTTPResponse res = new HTTPResponse(out, dataOut);
            
            long start = System.currentTimeMillis();
            System.out.println("processing " + req.getRequestURI());
            
            factory.newInstance().process(req, res);
            
            System.out.println("processed  " + req.getRequestURI() + 
                    " in " + (System.currentTimeMillis() - start) + "ms");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                socket.close(); // we close socket connection
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public static void start(HTTPProcessorFactory factory, int port, boolean secure) {
        
        try {
            executor = Executors.newFixedThreadPool(NFILS);
            
            ServerSocketFactory socketFactory = secure?
                    SSLServerSocketFactory.getDefault() : ServerSocketFactory.getDefault();
            
            ServerSocket serverSocket = socketFactory.createServerSocket(port);
            
            while (true) {
                SocketHTTPServer myServer = 
                        new SocketHTTPServer(serverSocket.accept(), factory);
                
                Runnable tasca = new Runnable() {
                    @Override
                    public void run() {
                        myServer.handle();
                    }
                };                
                executor.execute(tasca);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
