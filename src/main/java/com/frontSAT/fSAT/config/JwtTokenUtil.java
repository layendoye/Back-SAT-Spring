package com.frontSAT.fSAT.config;
//code copier de : https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
//recuperer le code de son pom.xml (ligne 30 à 34)
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.frontSAT.fSAT.model.Role;
import com.frontSAT.fSAT.model.RoleName;
import com.frontSAT.fSAT.model.User;
import com.frontSAT.fSAT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtTokenUtil implements Serializable {//pour cr
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken(UserDetails userDetails) throws Exception {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    @Autowired
    UserRepository userRepository;
    //while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) throws Exception {
        User user= userRepository.findByUsername(subject).orElseThrow();
        String roleUser = null;
        for (Role role: user.getRoles()) {
            RoleName leRole=role.getName();
            roleUser=leRole.name();
        }

        if(roleUser.equals("ROLE_Super_admin")){
            roleUser="ROLE_Super-admin";//pour angular
        }
        else if(roleUser.equals("ROLE_admin_Principal")){
            roleUser="ROLE_admin-Principal";
        }

        if(!user.getStatus().equals("Actif")){
            throw new Exception("Ce compte est bloqué, veuillez contacter l'administrateur !!");
        }
        if(!user.getEntreprise().getStatus().equals("Actif")){
            throw new Exception("Ce partenaire est bloqué, veuillez contacter la société SA Transfert !!");
        }

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).claim("roles",roleUser)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
