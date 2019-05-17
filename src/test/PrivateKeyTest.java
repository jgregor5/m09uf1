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
        
        String result;
        
        String text1 = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";
        String text2 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        
        result = test(text1);
        System.out.println("result: " + result);
        result = test(text2);
        System.out.println("result: " + result);
    }
    
    public static String test(String text) throws Exception {
        
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        
        byte[] encrypted = encrypt(secretKey, text.getBytes("UTF-8"));        
        byte[] decrypted = decrypt(secretKey, encrypted);
                
        String result = new String(decrypted, "UTF-8");        
        return result;
    }
 
    public static byte[] encrypt(Key secretKey, byte[] data) throws Exception {
        
        Cipher cipher = Cipher.getInstance("DES");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        byte[] encrypted = cipher.doFinal(data);
        return encrypted;
    }
    
    public static byte[] decrypt(Key secretKey, byte[] data) throws Exception {
        
        Cipher cipher = Cipher.getInstance("DES");        
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        byte[] decrypted = cipher.doFinal(data);
        return decrypted;
    }
}
