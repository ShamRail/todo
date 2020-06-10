package start.todo.service.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import start.todo.model.domain.User;
import start.todo.model.dto.UserRegistrationDTO;
import start.todo.repo.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public User save(UserRegistrationDTO u) {
        String userEmail = u.getEmail();
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            return null;
        }

        User saving = new User();
        saving.setUsername(u.getUsername());
        saving.setEmail(u.getEmail());
        saving.setPassword(passwordEncoder.encode(u.getPassword()));
        saving.setRegistrationDate(LocalDateTime.now());

        return userRepository.save(saving);
    }

}

