package start.todo.service.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.Statistic;
import start.todo.model.domain.*;
import start.todo.service.CategoryService;
import start.todo.service.GroupService;
import start.todo.service.ProjectService;
import start.todo.service.TaskService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GroupService groupService;

    @Override
    public List<Task> allTasks(User user) {
        return projectService.userProjects(user)
                .stream()
                .flatMap(p -> taskService.loadWithStructure(p).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> tasksByProject(Project project) {
        return taskService.findByProject(project);
    }

    @Override
    public List<Category> categoriesByProject(Project project) {
        return categoryService.findByProject(project);
    }

    @Override
    public List<Group> groupsByProject(Project project) {
        return groupService.findByProject(project);
    }

    @Override
    public Statistic formProjectStatistic(Project project) {
        List<Task> tasks = taskService.findByProject(project);
        return getStatistic(tasks);
    }

    @Override
    public Statistic totalStatistic(User user) {
        List<Project> projects = projectService.userProjects(user);
        int total = 0;
        int completed = 0;
        int outdated = 0;
        Statistic statistic = new Statistic();
        for (Project project : projects) {
            Statistic sts = formProjectStatistic(project);
            statistic.getStatistics().add(sts);
            total += sts.getTotal();
            completed += sts.getCompleted();
            outdated += sts.getOutDated();
        }
        return statistic
                .total(total)
                .completed(completed)
                .outDate(outdated);
    }

    private Statistic getStatistic(List<Task> tasks) {
        int total = tasks.size();
        int complete = 0;
        int outDate = 0;
        LocalDate today = LocalDate.now();
        for (Task task : tasks) {
            if (task.getStatus().equals(TaskStatus.COMPLETED)) {
                complete++;
            }
            if ((TaskStatus.IN_PROGRESS.equals(task.getStatus()))
                    && (task.getExpiredDate() != null && task.getExpiredDate().isBefore(today))) {
                outDate++;
            }
        }
        return new Statistic()
                .total(total)
                .completed(complete)
                .outDate(outDate);
    }

    @Override
    public List<Task> todayTasks(User user) {
        LocalDate today = LocalDate.now();
        return findByPredicate(user, t -> t.getExpiredDate() != null && t.getExpiredDate().equals(today));
    }

    @Override
    public List<Task> thisWeekTasks(User user) {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusWeeks(1);
        return findByPredicate(user, t -> t.getExpiredDate() != null
                && ((t.getExpiredDate().isAfter(from) || t.getExpiredDate().equals(from))
                && (t.getExpiredDate().isBefore(to)) || t.getExpiredDate().equals(to)));
    }

    @Override
    public List<Task> outDatedTasks(User user) {
        LocalDate today = LocalDate.now();
        return findByPredicate(user, t -> t.getExpiredDate() != null && t.getExpiredDate().isBefore(today));
    }

    private List<Task> findByPredicate(User user, Predicate<Task> condition) {
        List<Project> userProjects = projectService.userProjects(user);
        return userProjects.stream()
                .flatMap(project -> taskService.loadWithStructure(project).stream())
                .filter(condition)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> loadByResponsible(User responsible) {
        return taskService.loadByResponsible(responsible);
    }
}
