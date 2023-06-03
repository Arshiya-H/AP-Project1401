package Authentication;


import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class JWTGenerator {
    public static String generateJWT(String subject, String issuer, String secretkey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        // Generate random 256-bit (32-byte) secure random number
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        // Get an HMAC-SHA256 Message Authentication Code (MAC)
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretkey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);

        // Create the token from the random bytes, the current time, and the issuer and the subject
        StringBuffer token = new StringBuffer(Base64.getEncoder().encodeToString(bytes));
        token.append(".");
        token.append(System.currentTimeMillis());
        token.append(".");
        token.append(Base64.getEncoder().encodeToString(subject.getBytes()));
        token.append(".");
        token.append(Base64.getEncoder().encodeToString(issuer.getBytes()));
        token.append(".");
        token.append(Base64.getEncoder().encodeToString(mac.doFinal(token.toString().getBytes())));

        return token.toString();
    }
}