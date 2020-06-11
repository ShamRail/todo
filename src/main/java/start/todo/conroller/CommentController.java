package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;
import start.todo.model.domain.User;
import start.todo.model.dto.CommentDTO;
import start.todo.model.view.ModelView;
import start.todo.service.CommentService;
import start.todo.service.TaskService;
import start.todo.service.UserService;

@RestController
@RequestMapping("api/user/{userId}/project/{projectId}/category/{categoryId}/group/{groupId}/task/{taskId}/comment")
public class CommentController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Comment createComment(
            @PathVariable("userId") Long userId,
            @PathVariable("taskId") Long taskId,
            @RequestBody CommentDTO commentDTO) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("invalid user id!");
        }
        if (taskService.findById(taskId) == null) {
            throw new ResourceNotFoundException("invalid task id!");
        }
        Comment comment = new Comment();
        mapper.map(commentDTO, comment);
        comment.setAuthor(user);
        comment.setTask(Task.idStub(taskId));
        return commentService.save(comment);
    }

}
