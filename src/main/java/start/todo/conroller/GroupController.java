package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.Category;
import start.todo.model.domain.Group;
import start.todo.model.domain.Project;
import start.todo.model.domain.Task;
import start.todo.model.dto.GroupDTO;
import start.todo.model.view.ModelView;
import start.todo.service.CategoryService;
import start.todo.service.GroupService;
import start.todo.service.ProjectService;
import start.todo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}/project/{projectId}/category/{categoryId}/group")
public class GroupController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Group createGroup(
            @PathVariable("projectId") Long projectId,
            @PathVariable("categoryId") Long categoryId,
            @RequestBody GroupDTO groupDTO) {
//        if (projectService.findById(projectId) == null) {
//            throw new ResourceNotFoundException("project not exist!");
//        }
//        if (categoryService.findById(categoryId) == null) {
//            throw new ResourceNotFoundException("category not exist!");
//        }
        Group group = new Group();
        mapper.map(groupDTO, group);
        group.setProject(Project.idStub(projectId));
        group.setCategory(Category.idStub(categoryId));
        return groupService.save(group);
    }

    @GetMapping("/{groupId}")
    @JsonView(ModelView.FieldsCategoryTasks.class)
    public Group loadById(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("groupId") Long groupId) {
        Category category = categoryService.findById(categoryId);
        Group group = groupService.findById(groupId);
        if (category == null || group == null) {
            throw new ResourceNotFoundException("Invalid category or group id");
        }
        List<Task> tasks = taskService.tasks(group);
        group.setCategory(category);
        group.setTasks(tasks);
        return group;
    }

}
