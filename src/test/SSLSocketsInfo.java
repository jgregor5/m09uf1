package test;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author julian
 */
public class SSLSocketsInfo {

    /* 
    
    1) sense client auth: 
       - serverKeystore.jks: clau pública/privada del servidor
       - clientTruststore.jks: clau pública del servidor
    
    keytool -genkey -alias srvAlias -keyalg RSA -keystore serverKeystore.jks -keysize 2048
    keytool -export -keystore serverKeystore.jks -alias srvAlias -file server.crt
    keytool -importcert -file server.crt -keystore clientTruststore.jks -alias srvAlias
    
    CLIENT: javax.net.ssl.trustStore=clientTruststore.jks
    SERVER: javax.net.ssl.keyStore=serverKeystore.jks    
    
    2) amb client auth (afegim a l'anterior): 
        - clientKeystore.jks: clau pública/privada del client 
        - serverTruststore.jks: clau pública del client
    
    keytool -genkey -keyalg RSA -alias cltAlias -keystore clientKeystore.jks -storepass yourpassword -keysize 2048
    keytool -export -keystore clientKeystore.jks -alias cltAlias -file cliente.crt
    keytool -importcert -file cliente.crt -keystore serverTruststore.jks -alias cltAlias
    
    CLIENT: javax.net.ssl.keyStore=clientKeystore.jks
    SERVER: javax.net.ssl.trustStore=serverTruststore.jks
    
     */
    public static void main(String[] args) {
        
        showSecurityInfo();
    }

    private static void showSecurityInfo() {
        
        Set<String> types = new HashSet<>();
        
        Provider[] providerList = Security.getProviders();
        for (Provider provider : providerList) {
            System.out.println(new String(new char[80]).replace('\0', '='));
            System.out.println("Name: " + provider.getName());
            System.out.println("Class: " + provider.getClass());
            System.out.println("Information:\n" + provider.getInfo());
            System.out.println(new String(new char[80]).replace('\0', '-'));
            Set<Service> serviceList = provider.getServices();
            for (Service service : serviceList) {
                types.add(service.getType());
                System.out.println("\t" + service.getType() + ": " + service.getAlgorithm());
            }
        }
        
        System.out.println("\n\nTypes: " + types);
    }
}
