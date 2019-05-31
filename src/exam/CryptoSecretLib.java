package exam;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author julian
 */
public class CryptoSecretLib {
    
    // la llibreria i les dues claus RSA
    
    public static final String RSA_PUBLIC = 
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDeERKPJZSg1BA6dxVYHLTem4bHs0"
            + "EbCnOGOtD25m2LpyJQawGCR/qvFPwyRCUXo9Vjy1XXDYtpk9Cel5547a2dupOCikr"
            + "K8PIJMVbOFAmyBVl9qM5DU1KuVyh428oKvjZiU81330+uFC5Y3AN+zhGETo6t5onm"
            + "VZVCQ4q32cgkwIDAQAB";    
    
    public static final String RSA_PRIVATE = 
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMN4REo8llKDUEDp3FV"
            + "gctN6bhsezQRsKc4Y60PbmbYunIlBrAYJH+q8U/DJEJRej1WPLVdcNi2mT0J6Xnnj"
            + "trZ26k4KKSsrw8gkxVs4UCbIFWX2ozkNTUq5XKHjbygq+NmJTzXffT64ULljcA37O"
            + "EYROjq3mieZVlUJDirfZyCTAgMBAAECgYB17O62IstFyHb4Up5c260qrKgUsIZbbq"
            + "3h6uK0s52ObZx+qiIQGh0pTqkNkR/xgwLSSWi/VaBR9g3HO3AX9Sta5jTa0Z3GKE9"
            + "lKiqXLsQU0WzIH1d2WOk3EJ4Ml8kk6sReNF7CyFBapLN4nDP8p2T5TlFnBFTlYye3"
            + "2wQhax8FIQJBAPbh7smMyOrphiW6elftei43a9Tz7WB7fZBKR9Ly75If2VB8FFjBV"
            + "3lUE7oaa3r31487eqEfweJ3zfGumaflDKkCQQDKsEUAJOL8A/wVmitrGyFsGx1rGT"
            + "me92GW5xeo+yXv83n4EhWUcLLvl6UbU2BBR8UApnbpWXSX0m0fZvIY7mzbAkEAwZr"
            + "YKZKQFThgjMG6I64FvKyVkYBgzOLKyxaVUKZWnV8Aio9jXV7xfCIk/jKtytcFYjug"
            + "pS5EW5bcGAV2ViXXUQJARbdnpGsLn/+G369Nq+ODFXomHkZo6WLRsGQAYWExZV1r4"
            + "IXn4kz5Xyvb4JBHFW0oN9cKI61fSzFX02tHbofj7QJASoWKUbqkZtmZza4dD99moM"
            + "l87MbqPEi5dccdw73kOFe7NpIvkmxs9XsFSMH6jYF50lW9LktAMusGmVBJL5X6aA==";

    public static String sendPublicKey(PublicKey key) throws Exception {
        
        return bytesToBase64(key.getEncoded());
    }
    
    public static String sendEncryptedSecretKey(SecretKey sk) throws Exception {
        
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pubKey = importPublicKey(kf, RSA_PUBLIC);
        
        Cipher ci = Cipher.getInstance("RSA");
        ci.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] encodedSecretKey = ci.doFinal(sk.getEncoded());
        
        return bytesToBase64(encodedSecretKey);
    }    
    
    public static String sendEncryptedMessage(SecretKey sk, String text) throws Exception {
        
        Cipher ci = Cipher.getInstance("DES");
        ci.init(Cipher.ENCRYPT_MODE, sk);
        byte[] encodedMessage = ci.doFinal(text.getBytes("UTF-8"));
        
        return bytesToBase64(encodedMessage);
    }     
    
    // UTILS
    
    private static String bytesToBase64(byte[] hash) {        
        return Base64.getEncoder().encodeToString(hash);
    }
    
    private static PublicKey importPublicKey(KeyFactory kf, String publicB64Key) 
            throws InvalidKeySpecException {
        
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(CryptoUtils.base64ToBytes(publicB64Key));
        PublicKey publicKey = kf.generatePublic(keySpecX509);
        return publicKey;
    }     
}
