package recipes.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public User save(User toSave) {
        return userRepository.save(toSave);
    }


}
