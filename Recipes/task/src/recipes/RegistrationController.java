package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.users.User;
import recipes.users.UserService;

import javax.validation.Valid;

@Validated
@RestController
public class RegistrationController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@"
                + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$";

        if (user.getPassword().length() < 8 || user.getPassword().isBlank() || !user.getEmail().matches(regexPattern)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            if (userService.findUserByUsername(user.getEmail()) == null) {
                userService.save(user);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

}
