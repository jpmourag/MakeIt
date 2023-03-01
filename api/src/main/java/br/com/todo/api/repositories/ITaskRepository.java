package br.com.todo.api.repositories;

import br.com.todo.api.entities.Task;
import br.com.todo.api.modules.task.dto.TaskPaginationDto;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
public interface ITaskRepository extends JpaRepository<Task, UUID> {
}
