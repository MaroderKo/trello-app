package spd.trello.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTTokenProvider {

    @Autowired
    private UserDetailService service;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.header}")
    private String authorizationHeader;

    @Autowired
    private BCryptPasswordEncoder encoder;

    protected void init()
    {
        secretKey = encoder.encode(secretKey);
    }

    public String createToken(String login, UUID userId)
    {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("userId", userId);
        Date created = new Date();
        Date expired = new Date(created.getTime() + 259200*1000);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(created)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token)
    {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token){
        UserDetails details = this.service.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
    }

    public String getUserName(String token)
    {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request)
    {
        return request.getHeader(authorizationHeader);
    }
}
