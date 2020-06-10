package start.todo.service;

import start.todo.model.domain.User;
import start.todo.service.crud.BasicCRUD;

public interface UserService extends BasicCRUD<User> {
    User findByEmail(String email);
}
