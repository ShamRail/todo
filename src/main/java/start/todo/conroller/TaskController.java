package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.*;
import start.todo.model.dto.TaskDTO;
import start.todo.model.view.ModelView;
import start.todo.service.*;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}/project/{projectId}/category/{categoryId}/group/{groupId}/task")
public class TaskController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Task createTask(
            @PathVariable("projectId") Long projectId,
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("groupId") Long groupId,
            @RequestBody TaskDTO taskDTO) {
//        if (projectService.findById(projectId) == null) {
//            throw new ResourceNotFoundException("invalid project id!");
//        }
//        if (categoryService.findById(categoryId) == null) {
//            throw new ResourceNotFoundException("invalid category id!");
//        }
//        if (groupService.findById(groupId) == null) {
//            throw new ResourceNotFoundException("invalid group id!");
//        }
        Task task = new Task();
        mapper.map(taskDTO, task);
        task.path(
                Project.idStub(projectId), Category.idStub(categoryId), Group.idStub(groupId)
        );
        task.setContent(new TaskContent(taskDTO.getContent()));
        return taskService.save(task);
    }

    @GetMapping("/{taskId}")
    @JsonView(ModelView.FieldsGroupContentComments.class)
    public Task loadById(
            @PathVariable("groupId") Long groupId,
            @PathVariable("taskId") Long taskId) {
        Task task = taskService.loadWithContent(taskId);
        Group group = groupService.findById(groupId);
        if (task == null || group == null) {
            throw new ResourceNotFoundException("invalid task or group id");
        }
        List<Comment> comments = commentService.comments(task);
        task.setComments(comments);
        task.setGroup(group);
        return task;
    }
}
