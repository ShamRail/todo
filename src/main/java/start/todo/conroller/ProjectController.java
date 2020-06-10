package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import start.todo.exception.InvalidArgsException;
import start.todo.exception.ResourceNotFoundException;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;
import start.todo.model.dto.ProjectDTO;
import start.todo.model.view.ModelView;
import start.todo.service.CategoryService;
import start.todo.service.ProjectService;
import start.todo.service.UserService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/user/{userId}/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Project createProject(@PathVariable("userId") Long userId, @RequestBody ProjectDTO projectDTO) {
        User user = User.idStub(userId);
        Project project = new Project(
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                LocalDateTime.now()
        );
        if (!projectService.createProject(user, project)) {
            throw new InvalidArgsException("Invalid userId");
        }
        return project;
    }

    @GetMapping
    @JsonView(ModelView.BasicFields.class)
    public List<Project> userProjects(@PathVariable("userId") Long userId) {
        return projectService.userProjects(User.idStub(userId));
    }

    @GetMapping("/{projectId}")
    public Project fullProject(
            @PathVariable("userId") Long userId,
            @PathVariable("projectId") Long projectId) {
        Project project = projectService.findById(projectId);
        User user = userService.findById(userId);
        if (project == null || user == null) {
            throw new ResourceNotFoundException("Invalid user or project id!");
        }
        Project out = new Project();
        mapper.map(project, out);
        out.setCategories(categoryService.categories(project));
        return out;
    }
}
