package test;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author julian
 */
public class PrivateKeyTest {
    
    public static void main(String[] args) throws Exception {
        
        String text = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";
        
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        
        byte[] encrypted = encrypt(secretKey, text.getBytes("UTF-8"));        
        byte[] decrypted = decrypt(secretKey, encrypted);
        
        String result = new String(decrypted, "UTF-8");
        System.out.println(result);
    }
 
    public static byte[] encrypt(Key secretKey, byte[] data) throws Exception {
        
        Cipher cipher = Cipher.getInstance("AES");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        byte[] encrypted = cipher.doFinal(data);
        return encrypted;
    }
    
    public static byte[] decrypt(Key secretKey, byte[] data) throws Exception {
        
        Cipher cipher = Cipher.getInstance("AES");        
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        byte[] decrypted = cipher.doFinal(data);
        return decrypted;
    }
}
