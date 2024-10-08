//package hello.tobagi.tobagi.login.controller;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import javax.security.auth.login.LoginException;
//import java.util.Date;
//
//@Component
//public class JwtTokenGenerator {
//
//    private final Dotenv dotenv;
//    private final String secretKey;
//    private final long expireTimeMilliSecond;
//
//    public JwtTokenGenerator() {
//        this.dotenv = Dotenv.configure().load();
//        this.secretKey = dotenv.get("JWT_SECRET_KEY");
//        this.expireTimeMilliSecond = Long.parseLong(dotenv.get("JWT_EXPIRE_LENGTH").trim());
//    }
//
//    public String generateToken(final String id) {
//        final Claims claims = Jwts.claims();
//        claims.put("memberId", id);
//        final Date now = new Date();
//        final Date expiredDate = new Date(now.getTime() + expireTimeMilliSecond);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(expiredDate)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    public String extractMemberId(final String token) throws LoginException {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .get("memberId")
//                    .toString();
//        } catch (final Exception error) {
//            throw new LoginException("Invalid access token");
//        }
//    }
//}