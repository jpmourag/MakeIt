package br.com.todo.api.modules.task;

import br.com.todo.api.entities.Task;
import br.com.todo.api.exceptions.throwables.entity.FolderException;
import br.com.todo.api.global.dto.PaginationBaseResponseDto;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.modules.folder.FolderService;
import br.com.todo.api.modules.task.dto.CreateTaskDto;
import br.com.todo.api.modules.task.dto.TaskPaginationDto;
import br.com.todo.api.modules.task.dto.UpdateTaskDto;
import br.com.todo.api.modules.user.UserService;
import br.com.todo.api.repositories.ITaskRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO
// # criar/pegar/deletar/atualizar task
// pegar quantidade total de task q o folder tem
// pegar range de task pelo id do folder - pagination

@Service
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private FolderService folderService;
    @Autowired
    private UserService userService;

    public ResponseBaseDto createTask(CreateTaskDto taskToCreate, String token) throws FolderException {
        var folder = folderService.getFolderById(UUID.fromString(taskToCreate.getFolderId()), token);
        if (folder == null) throw new FolderException("Folder not found");
        var task = Task.builder()
                .title(taskToCreate.getTitle())
                .description(taskToCreate.getDescription())
                .folder(folder)
                .isCompleted(false)
                .build();
        var savedTask = taskRepository.save(task);
        Map<String, String> data = new HashMap<>();
        data.put("id", savedTask.getId().toString());
        data.put("title", savedTask.getTitle());
        data.put("description", savedTask.getDescription());
        data.put("folder_id", savedTask.getFolder().getId().toString());
        data.put("folder_title", savedTask.getFolder().getTitle());
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(data)
                .message("Task created")
                .build();
    }
    public ResponseBaseDto deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task deleted")
                .build();
    }

    public ResponseBaseDto updateTask(UUID taskId, UpdateTaskDto taskToUpdate, String token) {
        var task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseBaseDto.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Task not found")
                    .build();
        }
        task.setTitle(taskToUpdate.getTitle());
        task.setDescription(taskToUpdate.getDescription());
        task.setIsCompleted(taskToUpdate.getIsCompleted());
        taskRepository.save(task);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task updated")
                .build();
    }

    // Funções auxiliares abaixo sem response
    private Task getFilteredTaskById(UUID taskId, String token) {
        var email = userService.getEmailFromToken(token);
        return taskRepository.findByIdFiltered(taskId, email);
    }
}
