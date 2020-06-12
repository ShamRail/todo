package start.todo.service.dashboard;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.Statistic;
import start.todo.model.domain.*;
import start.todo.service.CategoryService;
import start.todo.service.GroupService;
import start.todo.service.ProjectService;
import start.todo.service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class DashboardServiceImplTest {

    @TestConfiguration
    static class DashboardServiceImplTestContextConfiguration {

        @Bean
        public DashboardService dashboardService() {
            return new DashboardServiceImpl();
        }
    }

    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private TaskService taskService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private GroupService groupService;

    @Test
    public void allTasks() {
        User user = User.idStub(1L);
        Project projectOne = new Project(1L);
        projectOne.setTitle("one");
        Project projectTwo = new Project(2L);
        projectTwo.setTitle("two");
        List<Project> userProjects = List.of(projectOne, projectTwo);
        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(2));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());

        Mockito.when(projectService.userProjects(user)).thenReturn(userProjects);
        Mockito.when(taskService.loadWithStructure(projectOne)).thenReturn(List.of(task1, task2));
        Mockito.when(taskService.loadWithStructure(projectTwo)).thenReturn(List.of(task3, task4));

        List<Task> result = dashboardService.allTasks(user);

        Assert.assertThat(result, Is.is(List.of(task1, task2, task3, task4)));
    }

    @Test
    public void tasksByProject() {
        Project projectOne = new Project(1L);
        Project projectTwo = new Project(2L);

        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(2));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());

        Mockito.when(taskService.findByProject(projectOne)).thenReturn(List.of(task1, task2));
        Mockito.when(taskService.findByProject(projectTwo)).thenReturn(List.of(task3, task4));

        List<Task> result = dashboardService.tasksByProject(projectOne);
        Assert.assertThat(result.size(), Is.is(2));
        Assert.assertThat(result.get(0), Is.is(task1));
        Assert.assertThat(result.get(1), Is.is(task2));
    }

    @Test
    public void categoriesByProject() {
        Project projectOne = new Project(1L);
        Category categoryOne = new Category(1L);
        categoryOne.setProject(projectOne);
        Category categoryTwo = new Category(2L);
        categoryTwo.setProject(projectOne);

        Mockito.when(categoryService.findByProject(projectOne)).thenReturn(List.of(categoryOne, categoryTwo));

        Assert.assertThat(
                dashboardService.categoriesByProject(projectOne),
                Is.is(List.of(categoryOne, categoryTwo))
        );
    }

    @Test
    public void groupsByProject() {
        Project projectOne = new Project(1L);
        Group groupOne = new Group(1L);
        groupOne.setProject(projectOne);
        Group groupTwo = new Group(2L);
        groupTwo.setProject(projectOne);

        Mockito.when(groupService.findByProject(projectOne)).thenReturn(List.of(groupOne, groupTwo));

        Assert.assertThat(
                dashboardService.groupsByProject(projectOne),
                Is.is(List.of(groupOne, groupTwo))
        );
    }

    @Test
    public void formProjectStatistic() {
        Project projectOne = new Project(1L);

        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusWeeks(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        task2.setStatus(TaskStatus.COMPLETED);
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(7));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusMonths(1));

        Mockito.when(taskService.findByProject(projectOne)).thenReturn(List.of(task1, task2, task3, task4));

        Statistic statistic = dashboardService.formProjectStatistic(projectOne);

        Assert.assertEquals(statistic.getTotal(), 4L);
        Assert.assertEquals(statistic.getCompleted(), 1);
        Assert.assertEquals(statistic.getOutDated(), 2);
    }

    @Test
    public void totalStatistic() {
        User user = User.idStub(1L);
        Project projectOne = new Project(1L);
        Project projectTwo = new Project(2L);
        List<Project> userProjects = List.of(projectOne, projectTwo);

        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusWeeks(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        task2.setStatus(TaskStatus.COMPLETED);
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(7));

        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusMonths(1));
        Task task5 = new Task(5L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(2));
        task5.setStatus(TaskStatus.COMPLETED);
        Task task6 = new Task(6L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(3));


        Mockito.when(projectService.userProjects(user)).thenReturn(userProjects);
        Mockito.when(taskService.findByProject(projectOne)).thenReturn(List.of(task1, task2, task3));
        Mockito.when(taskService.findByProject(projectTwo)).thenReturn(List.of(task4, task5, task6));

        Statistic statistic = dashboardService.totalStatistic(user);

        Assert.assertEquals(statistic.getTotal(), 6L);
        Assert.assertEquals(statistic.getCompleted(), 2);
        Assert.assertEquals(statistic.getOutDated(), 2);
        Assert.assertEquals(statistic.getStatistics().size(), 2);
    }

    @Test
    public void todayTasks() {
        User user = User.idStub(1L);
        Project projectOne = new Project(1L);
        Project projectTwo = new Project(2L);
        List<Project> userProjects = List.of(projectOne, projectTwo);
        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(2));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());

        Mockito.when(projectService.userProjects(user)).thenReturn(userProjects);
        Mockito.when(taskService.loadWithStructure(projectOne)).thenReturn(List.of(task1, task2));
        Mockito.when(taskService.loadWithStructure(projectTwo)).thenReturn(List.of(task3, task4));

        List<Task> result = dashboardService.todayTasks(user);
        Assert.assertThat(result.size(), Is.is(2));
        Assert.assertThat(result.get(0), Is.is(task2));
        Assert.assertThat(result.get(1), Is.is(task4));
    }

    @Test
    public void thisWeekTasks() {
        User user = User.idStub(1L);
        Project projectOne = new Project(1L);
        Project projectTwo = new Project(2L);
        List<Project> userProjects = List.of(projectOne, projectTwo);

        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now());
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(7));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(10));

        Mockito.when(projectService.userProjects(user)).thenReturn(userProjects);
        Mockito.when(taskService.loadWithStructure(projectOne)).thenReturn(List.of(task1, task2));
        Mockito.when(taskService.loadWithStructure(projectTwo)).thenReturn(List.of(task3, task4));

        List<Task> result = dashboardService.thisWeekTasks(user);
        Assert.assertThat(result.size(), Is.is(3));
        Assert.assertThat(result.get(0), Is.is(task1));
        Assert.assertThat(result.get(1), Is.is(task2));
        Assert.assertThat(result.get(2), Is.is(task3));
    }

    @Test
    public void outDatedTasks() {
        User user = User.idStub(1L);
        Project projectOne = new Project(1L);
        Project projectTwo = new Project(2L);
        List<Project> userProjects = List.of(projectOne, projectTwo);

        Task task1 = new Task(1L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusWeeks(1));
        Task task2 = new Task(2L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(1));
        Task task3 = new Task(3L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().plusDays(7));
        Task task4 = new Task(4L).createAndExpiredDates(LocalDateTime.now(), LocalDate.now().minusMonths(1));

        Mockito.when(projectService.userProjects(user)).thenReturn(userProjects);
        Mockito.when(taskService.loadWithStructure(projectOne)).thenReturn(List.of(task1, task2));
        Mockito.when(taskService.loadWithStructure(projectTwo)).thenReturn(List.of(task3, task4));

        List<Task> result = dashboardService.outDatedTasks(user);
        Assert.assertThat(result.size(), Is.is(2));
        Assert.assertThat(result.get(0), Is.is(task1));
        Assert.assertThat(result.get(1), Is.is(task4));
    }
}