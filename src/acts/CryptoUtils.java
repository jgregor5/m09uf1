package acts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author julian
 */
public class CryptoUtils {
    
    private CryptoUtils() {}
    
    // random
    
    public static byte[] randomBytes(int size) {
    
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[size];
        random.nextBytes(salt);
        
        return salt;
    }
    
    // secret
    
    public static SecretKey generateSecretKey(String algorithm, int size) 
            throws NoSuchAlgorithmException {
     
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(size); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }
    
    public static SecretKey importSecretKey(String algorithm, String base64Key) {        
        byte[] arrayKey = Base64.getDecoder().decode(base64Key);        
        return new SecretKeySpec(arrayKey, algorithm);
    }
    
    // public
    
    public static KeyPair generateKeyPair(String algorithm, int size) 
            throws NoSuchAlgorithmException {
        
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(size);
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }    
    
    public static PrivateKey importPrivateKey(KeyFactory kf, String privateB64Key) 
            throws InvalidKeySpecException {
        
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(CryptoUtils.base64ToBytes(privateB64Key));
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);
        return privateKey;
    }
    
    public static PublicKey importPublicKey(KeyFactory kf, String publicB64Key) 
            throws InvalidKeySpecException {
        
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(CryptoUtils.base64ToBytes(publicB64Key));
        PublicKey publicKey = kf.generatePublic(keySpecX509);
        return publicKey;
    }    
    
    public static KeyPair importKeyPair(String algorithm, String publicB64Key, String privateB64Key) 
            throws NoSuchAlgorithmException, InvalidKeySpecException              {
        
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        
        PublicKey publicKey = importPublicKey(kf, publicB64Key);
        PrivateKey privateKey = importPrivateKey(kf, privateB64Key);
        
        KeyPair kp = new KeyPair(publicKey, privateKey);
        return kp;
    }
    
    // digest
    
    public static byte[] digest(String algorithm, byte[] input) throws NoSuchAlgorithmException {
        
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] digest = messageDigest.digest(input);
        return digest;
    }

    // signature
    
    public static byte[] doSignature(String algorithm, PrivateKey privateKey, byte[] input) 
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        
        Signature sign = Signature.getInstance(algorithm);
        
        sign.initSign(privateKey);        
        sign.update(input);
        return sign.sign();
    }
    
    public static boolean verifySignature(String algorithm, PublicKey publicKey, byte[] input, byte[] signed) 
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        
        Signature sign = Signature.getInstance(algorithm);
        
        sign.initVerify(publicKey);
        sign.update(input);
        return sign.verify(signed);
    }    
    
    // keystore
    
    public static KeyStore loadKeyStore(String filename, String passwd) 
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {

        char c[] = new char[passwd.length()];
        passwd.getChars(0, c.length, c, 0);

        // same passwd for keystore and key
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fis = new FileInputStream(filename);
        ks.load(fis, c);

        return ks;
    }    
    
    // string array conversion
    
    public static byte[] keyToBytes(Key myKey) {        
        return Base64.getEncoder().encode(myKey.getEncoded());
    }
    
    public static String keyToBase64(Key myKey) {        
        return Base64.getEncoder().encodeToString(myKey.getEncoded());
    }
    
    public static byte[] base64ToBytes(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }
    
    public static String bytesToBase64(byte[] hash) {        
        return Base64.getEncoder().encodeToString(hash);
    }
    
    public static String bytesToHex(byte[] hash) {
        
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    // files
    
    public static String getAbsoluteFilename(String relativePath) throws FileNotFoundException {
        
        File file = new File(System.getProperty("user.dir"), relativePath);
        if (!file.exists()) {
            throw new FileNotFoundException(relativePath);
        }
        return file.getAbsolutePath();
    }       
}
