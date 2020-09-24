package mk.learning.services;

import lombok.extern.slf4j.Slf4j;
import mk.learning.dao.User;
import mk.learning.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(input);
        if(input.equalsIgnoreCase("gToken")) {
            log.info("user is gToken");
            return org.springframework.security.core.userdetails.User.builder()
                    .password(passwordEncoder.encode("password"))
                    .accountExpired(false)
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .authorities("admin")
                    .disabled(false)
                    .roles("SAMPLE_ROLE")
                    .username(input)
                    .build();
        }
        if (user == null) {
            throw new BadCredentialsException("Bad credentials");
        } else
        return org.springframework.security.core.userdetails.User.builder()
                .password(user.getPassword())
                .accountExpired(user.getAccountExpired())
                .credentialsExpired(user.getCredentialsExpired())
                .accountLocked(user.getAccountLocked())
                .authorities("admin")
                .disabled(!user.getEnabled())
                .roles("SAMPLE_ROLE")
                .username(input)
                .build();
    }
}
