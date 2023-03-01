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

    @Query("SELECT COUNT(*) FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email")
    Integer getAmountOfAllTasks(String email);

    @Query("SELECT COUNT(*) FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "t.isCompleted = :isCompleted")
    Integer getAmountOfTasksFilteredByIsCompleted(String email, Boolean isCompleted);

    @Query("SELECT COUNT(*) FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email " +
            "AND f.id = :folderId")
    Integer getAmountOfTasksByFolderId(UUID folderId, String email);

    @Query("SELECT COUNT(*) FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "f.id = :folderId AND " +
            "t.isCompleted = :isCompleted")
    Integer getAmountOfTasksByFolderIdFilteredByIsCompleted(UUID folderId, String email, Boolean isCompleted);


    // pagination
    @Query("SELECT new br.com.todo.api.modules.task.dto." +
            "TaskPaginationDto(t.id, t.title, t.description, t.isCompleted, " +
            "t.folder.title, t.folder.id, t.createdAt) FROM TASK t " +
            "WHERE t.folder.user.email = :email AND " +
            "(LOWER(t.title) LIKE LOWER(CONCAT('%', :key, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :key, '%')))")
    List<TaskPaginationDto> findByKeyword(String key, String email, AbstractPageRequest pageable);

    @Query("SELECT new br.com.todo.api.modules.task.dto." +
            "TaskPaginationDto(t.id, t.title, t.description, t.isCompleted, " +
            "f.title, f.id, t.createdAt) " +
            "FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "f.id = :folderId ORDER BY t.createdAt DESC")
    List<TaskPaginationDto> findAllTasksByFolderId(UUID folderId, String email, AbstractPageRequest pageable);

    @Query("SELECT new br.com.todo.api.modules.task.dto." +
            "TaskPaginationDto(t.id, t.title, t.description, t.isCompleted, " +
            "f.title, f.id, t.createdAt) " +
            "FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "f.id = :folderId AND " +
            "t.isCompleted = :isCompleted ORDER BY t.createdAt DESC")
    List<TaskPaginationDto> findAllTasksByFolderIdFilteredByIsCompleted(UUID folderId,
                                                                        String email,
                                                                        Boolean isCompleted,
                                                                        AbstractPageRequest pageable);
    @Query("SELECT new br.com.todo.api.modules.task.dto." +
            "TaskPaginationDto(t.id, t.title, t.description, t.isCompleted, " +
            "f.title, f.id, t.createdAt) " +
            "FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email ORDER BY t.createdAt DESC")
    List<TaskPaginationDto> findAllTasks(String email, AbstractPageRequest pageable);

    @Query("SELECT new br.com.todo.api.modules.task.dto." +
            "TaskPaginationDto(t.id, t.title, t.description, t.isCompleted, " +
            "f.title, f.id, t.createdAt) " +
            "FROM TASK t " +
            "JOIN t.folder f " +
            "WHERE f.user.email = :email AND " +
            "t.isCompleted = :isCompleted ORDER BY t.createdAt DESC")
    List<TaskPaginationDto> findAllTasksFilteredByIsCompleted(String email,
                                                              Boolean isCompleted,
                                                              AbstractPageRequest pageable);
}
