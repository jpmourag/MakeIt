package br.com.todo.api.repositories;

import br.com.todo.api.entities.Task;
import br.com.todo.api.modules.task.dto.TaskPaginationDto;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
public interface ITaskRepository extends JpaRepository<Task, UUID> {
    @Query("SELECT t FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "t.id = :id")
    Task findByIdAndUserEmail(UUID id, String email);
    

    // amout of tasks
    @Query("SELECT COUNT(*) FROM TASK t " +
            "WHERE t.folder.user.email = :email AND " +
            "(LOWER(t.title) LIKE LOWER(CONCAT('%', :key, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :key, '%')))")
    Integer getAmountByKeyword(String key, String email);
}
