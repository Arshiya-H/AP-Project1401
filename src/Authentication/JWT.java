package Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;

public class JWT {

    public static boolean authenticateUser(String token, String secretkey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretkey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            // Check if the token is expired
            if (claims.getExpiration().before(new Date())) {
                return false;
            }
            // Token is valid
            return true;
        } catch (Exception e) {
            // Token is invalid
            return false;
        }
    }

    public static String generateJWT(String subject, String secretkey)
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
        token.append(Base64.getEncoder().encodeToString(("arshiya&mehdi").getBytes())); //the string is issuer.
        token.append(".");
        token.append(Base64.getEncoder().encodeToString(mac.doFinal(token.toString().getBytes())));

        return token.toString();
    }

    private static final int SECURITY_KEY_LENGTH = 256; // 256 bits

    public static String generateSecurityKey() {
        // Generate a secure random key
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[SECURITY_KEY_LENGTH / 8];
        random.nextBytes(keyBytes);

        // Encode the key as a Base64 string
        String securityKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);

        return securityKey;
    }

}
