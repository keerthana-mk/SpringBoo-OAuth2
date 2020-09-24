package mk.learning.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component("customAuthenticationManager")
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Map<String, String> parameters = (Map<String, String>) authentication.getDetails();
        if (String.valueOf(authentication.getPrincipal()).equalsIgnoreCase("gToken")) {
            return verifyToken(authentication, parameters.get("token"));
        } else {
            return verifyCredentials(authentication, String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials()));
        }

    }

    private Authentication verifyToken(Authentication authentication, String gToken) {
        log.info("verified token: {}", gToken);
        List<GrantedAuthority> authorities = Collections.singletonList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "SIMPLE_READ";
            }
        });
        return new UsernamePasswordAuthenticationToken(new User(String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials()), authorities), authentication.getCredentials(), authorities);
    }

    private Authentication verifyCredentials(Authentication authentication, String username, String password) {
        log.info("verified credentials");
        List<GrantedAuthority> authorities = Collections.singletonList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "SIMPLE_READ";
            }
        });
        return new UsernamePasswordAuthenticationToken(new User(String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials()), authorities), authentication.getCredentials(), authorities);
    }
}
