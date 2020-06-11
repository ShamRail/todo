package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.*;
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
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Comment createComment(
            @PathVariable("userId") Long userId,
            @PathVariable("projectId") Long projectId,
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("groupId") Long groupId,
            @PathVariable("taskId") Long taskId,
            @RequestBody CommentDTO commentDTO) {
        User user = userService.findById(userId);
        Comment comment = new Comment();
        mapper.map(commentDTO, comment);
        comment.setAuthor(user);
        comment.path(
                Project.idStub(projectId),
                Category.idStub(categoryId),
                Group.idStub(groupId),
                Task.idStub(taskId)
        );
        return commentService.save(comment);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentId);
        mapper.map(commentDTO, comment);
        if (!commentService.update(comment)) {
            throw new ResourceNotFoundException("Invalid id");
        }
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        if (!commentService.delete(commentId)) {
            throw new ResourceNotFoundException("Invalid id");
        }
    }

}
