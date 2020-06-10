package start.todo.conroller;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import start.todo.model.domain.User;
import start.todo.model.dto.UserRegistrationDTO;
import start.todo.model.jwt.JwtRequest;
import start.todo.model.jwt.JwtResponse;
import start.todo.model.view.ModelView;
import start.todo.service.jwt.JwtUserDetailsServiceImpl;
import start.todo.service.UserService;
import start.todo.util.JwtTokenUtil;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userServiceImpl.findByEmail(authenticationRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(token, user.getId()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @JsonView(ModelView.BasicFields.class)
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO user) throws Exception {
        User saveUsed = userDetailsService.save(user);
        if (saveUsed == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(saveUsed, HttpStatus.CREATED);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

