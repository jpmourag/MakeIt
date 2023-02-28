package br.com.todo.api.modules.task;

import br.com.todo.api.exceptions.CustomResponseEntityExceptionHandler;
import br.com.todo.api.exceptions.throwables.entity.EntityException;
import br.com.todo.api.exceptions.throwables.entity.TaskException;
import br.com.todo.api.exceptions.throwables.global.UnmappedErrorException;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.modules.task.dto.CreateTaskDto;
import br.com.todo.api.modules.task.dto.UpdateTaskDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/private/create")
    public ResponseEntity<ResponseBaseDto> create(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateTaskDto task
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.createTask(task, token));
        } catch (EntityException e) {
            throw new TaskException(e.getMessage());
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error creating task");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/get/{taskId}")
    public ResponseEntity<ResponseBaseDto> getTaskDataById(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID taskId
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.getTaskByIdResponse(taskId, token));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error getting task");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }
}
