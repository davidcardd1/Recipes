package recipes.users;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    void delete(User user);

    User findByEmail(String email);
}
