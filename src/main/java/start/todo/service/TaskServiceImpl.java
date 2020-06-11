package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.Group;
import start.todo.model.domain.Task;
import start.todo.model.domain.TaskContent;
import start.todo.model.domain.TaskStatus;
import start.todo.repo.TaskContentRepository;
import start.todo.repo.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskContentRepository contentDB;

    @Autowired
    private TaskRepository taskDB;

    @Override
    public List<Task> tasks(Group group) {
        return taskDB.findByGroup(group);
    }

    @Override
    public boolean markAs(Task task, TaskStatus status) {
        return taskDB.updateStatus(task.getId(), status) > 0;
    }

    @Override
    public Task loadWithContent(Long id) {
        return taskDB.taskWithContent(id);
    }

    @Override
    public boolean updateContent(Task task, TaskContent content) {
        return contentDB.update(task.getContent().getId(), content.getText()) > 0;
    }

    @Override
    public Task save(Task entity) {
        entity.setCreateDate(LocalDateTime.now());
        return taskDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        return taskDB.delete(id) > 0;
    }

    @Override
    public Task findById(Long id) {
        return taskDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(Task entity) {
        return taskDB.update(entity) > 0;
    }
}
