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
import start.todo.model.dto.CategoryDTO;
import start.todo.model.view.ModelView;
import start.todo.service.CategoryService;
import start.todo.service.GroupService;
import start.todo.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("api/user/{userId}/project/{projectId}/category")
public class CategoryController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ModelView.BasicFields.class)
    public Category createCategory(@PathVariable("projectId") Long projectId,
                                   @RequestBody CategoryDTO categoryDTO) {
        Project project = Project.idStub(projectId);
//        if (projectService.findById(projectId) == null) {
//            throw new ResourceNotFoundException("Invalid project id!");
//        }
        Category category = new Category();
        mapper.map(categoryDTO, category);
        category.setProject(project);
        return categoryService.save(category);
    }

    @GetMapping("/{categoryId}")
    @JsonView(ModelView.FieldsProjectGroups.class)
    public Category loadById(
            @PathVariable("projectId") Long projectId,
            @PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findById(categoryId);
        Project project = projectService.findById(projectId);
        if (category == null || project == null) {
            throw new ResourceNotFoundException("invalid category id!");
        }
        List<Group> groups = groupService.groups(category);
        category.setGroups(groups);
        category.setProject(project);
        return category;
    }

}
