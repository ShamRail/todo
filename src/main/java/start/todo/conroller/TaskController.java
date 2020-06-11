package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.Task;
import start.todo.model.domain.TaskContent;
import start.todo.model.domain.TaskStatus;
import start.todo.model.dto.TaskDTO;
import start.todo.model.view.ModelView;
import start.todo.service.CategoryService;
import start.todo.service.GroupService;
import start.todo.service.ProjectService;
import start.todo.service.TaskService;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Task createTask(
            @PathVariable("projectId") Long projectId,
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("groupId") Long groupId,
            @RequestBody TaskDTO taskDTO) {
        if (projectService.findById(projectId) == null) {
            throw new ResourceNotFoundException("invalid project id!");
        }
        if (categoryService.findById(categoryId) == null) {
            throw new ResourceNotFoundException("invalid category id!");
        }
        if (groupService.findById(groupId) == null) {
            throw new ResourceNotFoundException("invalid group id!");
        }
        Task task = new Task();
        mapper.map(taskDTO, task);
        task.setContent(new TaskContent(taskDTO.getContent()));
        return taskService.save(task);
    }

}
