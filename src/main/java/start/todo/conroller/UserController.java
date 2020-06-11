package start.todo.conroller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.User;
import start.todo.model.dto.UserDTO;
import start.todo.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO) {
        User user = new User(userId);
        mapper.map(userDTO, user);
        if (!userService.update(user)) {
            throw new ResourceNotFoundException("Invalid user id!");
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        if (!userService.delete(userId)) {
            throw new ResourceNotFoundException("Invalid user id!");
        }
    }

}
