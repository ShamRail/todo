package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.todo.model.domain.User;
import start.todo.repo.CommentRepository;
import start.todo.repo.UserProjectRepository;
import start.todo.repo.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userDB;

    @Autowired
    private UserProjectRepository userProjectDB;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProjectService projectService;

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
        projectService.userProjects(User.idStub(id)).forEach(
                p -> projectService.delete(p.getId())
        );
        userProjectDB.deleteByUser(User.idStub(id));
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
