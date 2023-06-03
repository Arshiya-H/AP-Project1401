package Authentication;

//public class Authentication {
//    public static void authenticateUser(String token)
//            throws Exception {
//        // decode the JWT
//        DecodedJWT decoded = JWT.decode(token);
//
//        // get userid from decoded JWT
//        String userId = decoded.getClaim("userid").asString();
//
//        // get user from database
//        User user = UserDao.findByUserId(userId);
//
//        // check credentials and authenticate user
//        if (user != null && user.authenticated(token)) {
//            return user;
//        } else {
//            return null;
//        }
//    }
//}

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTAuthenticator {
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
}


