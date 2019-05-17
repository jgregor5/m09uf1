package test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import javax.crypto.Cipher;

/**
 *
 * @author julian
 */
public class SignatureTest {
    
    public static void main(String[] args) throws Exception {
        
        customTest(); // using MessageDigest and Cipher
        libraryTest(); // using Signature
    }
    
    public static void customTest() throws Exception {
        
        String text = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";
        
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        
        // sign with private key
        
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digest = messageDigest.digest(text.getBytes());
        
        Cipher cipher = Cipher.getInstance("RSA");
        
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signed = cipher.doFinal(digest);
        
        // verify "signed" with public key
        
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decrypted = cipher.doFinal(signed);
        
        boolean verifies = Arrays.equals(digest, decrypted);
        System.out.println("custom test: " + verifies);
    }
    
    public static void libraryTest() throws Exception {
        
        String text = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";
        
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        
        Signature sign = Signature.getInstance("SHA256withRSA");
        
        // sign with private key
        
        sign.initSign(privateKey);        
        sign.update(text.getBytes("UTF-8"));
        byte[] signed = sign.sign();
        
        // verify "signed" with public key
        
        sign.initVerify(publicKey);
        sign.update(text.getBytes("UTF-8"));
        boolean verifies = sign.verify(signed);
        
        System.out.println("library test: " + verifies);
    }    
    
}
