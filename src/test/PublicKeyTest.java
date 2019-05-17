package test;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author julian
 */
public class PublicKeyTest {
    
    public static void main(String[] args) throws Exception {
        
        String result;
        
        String text1 = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";
        String text2 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        
        result = test(text1, true);
        System.out.println("result pub/pri: " + result);
        result = test(text1, false);
        System.out.println("result pri/pub: " + result);
        
        result = test(text2, true);
        System.out.println("result pub/pri: " + result);
        result = test(text2, false);
        System.out.println("result pri/pub: " + result);
    }

    private static String test(String text, boolean publicPrivate) throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        
        byte[] encrypted, decrypted;
        
        if (publicPrivate) {
            encrypted = encrypt(publicKey, text.getBytes("UTF-8"));
            decrypted = decrypt(privateKey, encrypted);
        }
        else {
            encrypted = encrypt(privateKey, text.getBytes("UTF-8"));
            decrypted = decrypt(publicKey, encrypted);
        }

        String result = new String(decrypted, "UTF-8");        
        return result;
    }

    public static byte[] encrypt(Key secretKey, byte[] data) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypted = cipher.doFinal(data);
        return encrypted;
    }

    public static byte[] decrypt(Key secretKey, byte[] data) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decrypted = cipher.doFinal(data);
        return decrypted;
    }
}
