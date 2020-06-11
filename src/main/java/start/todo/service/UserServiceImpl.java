package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.User;
import start.todo.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userDB;

    @Override
    public User findByEmail(String email) {
        return userDB.findByEmail(email);
    }

    @Override
    public User save(User entity) {
        return userDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        return userDB.delete(id) > 0;
    }

    @Override
    public User findById(Long id) {
        return userDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(User entity) {
        return userDB.update(entity) > 0;
    }
}
