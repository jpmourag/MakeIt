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

    // Funções auxiliares abaixo sem response
    private Task getFilteredTaskById(UUID taskId, String token) {
        var email = userService.getEmailFromToken(token);
        return taskRepository.findByIdFiltered(taskId, email);
    }
}
