package start.todo.conroller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import start.todo.model.Statistic;
import start.todo.model.domain.Project;
import start.todo.model.domain.Task;
import start.todo.model.domain.User;
import start.todo.model.view.ModelView;
import start.todo.service.dashboard.DashboardService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user/{userId}")
public class DashBoardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/project/today")
    @JsonView(ModelView.FieldsPath.class)
    public List<Task> todayTask(@PathVariable("userId") Long userId) {
        return dashboardService.todayTasks(User.idStub(userId));
    }

    @GetMapping("/project/outdate")
    @JsonView(ModelView.FieldsPath.class)
    public List<Task> outDate(@PathVariable("userId") Long userId) {
        return dashboardService.outDatedTasks(User.idStub(userId));
    }

    @GetMapping("/project/week")
    @JsonView(ModelView.FieldsPath.class)
    public List<Task> thisWeek(@PathVariable("userId") Long userId) {
        return dashboardService.thisWeekTasks(User.idStub(userId));
    }

    @GetMapping("/project/statistic")
    @JsonView(ModelView.BasicFields.class)
    public Map<String, Object> statistics(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) Long projectId) {
        Map<String, Object> responseBody;
        if (projectId == null) {
            Statistic statistic = dashboardService.totalStatistic(User.idStub(userId));
            responseBody = Map.of(
                    "total", statistic.getTotal(),
                    "completed", statistic.getCompleted(),
                    "outdate", statistic.getOutDated(),
                    "statistics", statistic.getStatistics()
            );
        } else {
            Statistic statistic = dashboardService.formProjectStatistic(Project.idStub(projectId));
            responseBody = Map.of(
                    "total", statistic.getTotal(),
                    "completed", statistic.getCompleted(),
                    "outdate", statistic.getOutDated()
            );
        }
        return responseBody;
    }

    @GetMapping("/project/all")
    @JsonView(ModelView.FieldsPath.class)
    public List<Task> allTasks(@PathVariable("userId") Long userId) {
        return dashboardService.allTasks(User.idStub(userId));
    }

}
