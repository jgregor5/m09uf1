package test;

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
    
}
