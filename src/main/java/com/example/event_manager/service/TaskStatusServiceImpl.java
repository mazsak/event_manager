package com.example.event_manager.service;

import com.example.event_manager.form.TaskStatusForm;
import com.example.event_manager.mapper.TaskStatusMapper;
import com.example.event_manager.model.Person;
import com.example.event_manager.model.TaskStatus;
import com.example.event_manager.repo.TaskStatusRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

  private final TaskStatusRepo taskStatusRepo;
  private final TaskStatusMapper taskStatusMapper;
  private final PersonService personService;


  @Override
  public boolean save(final TaskStatus taskStatus) {
    return taskStatusRepo.save(taskStatus) != null;
  }

  @Override
  public TaskStatusForm saveAndReturn(final TaskStatusForm taskStatus) {
    return taskStatusMapper.taskStatusToTaskStatusDto(taskStatusRepo.save(
        taskStatusMapper.toPOJO(taskStatus)));
  }

  @Override
  public void delete(final Long id) {
    taskStatusRepo.deleteById(id);
  }

  @Override
  public List<TaskStatusForm> findAll() {
    return taskStatusMapper.toDTOs(taskStatusRepo.findAll());
  }

  @Override
  public TaskStatusForm taskStatusFormById(final Long id) {
    return taskStatusMapper.taskStatusToTaskStatusDto(findById(id));
  }

  @Override
  public void update(final TaskStatusForm ts) {
    final TaskStatus toUpdate = findById(ts.getId());
    final Person person = personService.findById(ts.getPerson().getId());
    toUpdate.setStatus(ts.getStatus());
    toUpdate.setName(ts.getName());
    toUpdate.setDate(ts.getDate());
    toUpdate.setPerson(person);
    toUpdate.setTaskStatusType(ts.getTaskStatusType());
    save(toUpdate);
  }

  @Override
  public TaskStatus findById(final Long id) {
    return taskStatusRepo.findById(id).get();
  }
}
