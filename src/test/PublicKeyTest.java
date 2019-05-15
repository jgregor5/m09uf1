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

        String text = "Under canopy frost white in winter there'll be buds forming, waiting to greet the spring";

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        
        byte[] encrypted, decrypted;
        
        encrypted = encrypt(publicKey, text.getBytes("UTF-8"));
        decrypted = decrypt(privateKey, encrypted);

        String resultPubPri = new String(decrypted, "UTF-8");
        System.out.println(resultPubPri);
        
        encrypted = encrypt(privateKey, text.getBytes("UTF-8"));
        decrypted = decrypt(publicKey, encrypted);

        String resultPriPub = new String(decrypted, "UTF-8");
        System.out.println(resultPriPub);
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
