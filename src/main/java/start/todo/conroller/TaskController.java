package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.InvalidArgsException;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.*;
import start.todo.model.dto.TaskDTO;
import start.todo.model.view.ModelView;
import start.todo.service.*;
import start.todo.util.StatusParser;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/{taskId}")
    public void updateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDTO taskDTO) {
        Task task = new Task(taskId);
        mapper.map(taskDTO, task);
        if (!taskService.update(task)) {
            throw new ResourceNotFoundException("Invalid id");
        }
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        if (!taskService.delete(taskId)) {
            throw new ResourceNotFoundException("Invalid id");
        }
    }

    @PutMapping("/{taskId}/content")
    public void updateTask(@PathVariable("taskId") Long taskId, @RequestBody Map<String, String> body) {
        Task task = taskService.findById(taskId);
        TaskContent content = new TaskContent(body.get("text"));
        if (task == null || !taskService.updateContent(task, content)) {
            throw new ResourceNotFoundException("Invalid id");
        }
    }

    @PutMapping("/{taskId}/status")
    public void updateStatus(
            @PathVariable("taskId") Long taskId,
            @RequestParam String status) {
        TaskStatus sts = StatusParser.parse(status);
        if (sts == null) {
            throw new InvalidArgsException("Invalid status. Must be COMPLETED or IN_PROGRESS");
        }
        if (!taskService.markAs(Task.idStub(taskId), sts)) {
            throw new ResourceNotFoundException("Invalid id!");
        }
    }

}
