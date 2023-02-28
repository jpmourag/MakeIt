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

    @GetMapping("/private/find")
    public ResponseEntity<ResponseBaseDto> search(
            @RequestHeader("Authorization") String token,
            @RequestParam("filter") @Valid String filter,
            @RequestParam("offset") @Valid Integer offset,
            @RequestParam("pageSize") @Valid Integer pageSize
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.searchTask(filter, token, offset, pageSize));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error finding tasks");
        } catch (EmptyResultDataAccessException e) {
            throw new TaskException("Task not found");
        }  catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/pagination/{folderId}")
    public ResponseEntity<ResponseBaseDto> getTasksByFolderId(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID folderId,
            @RequestParam("offset") @Valid Integer offset,
            @RequestParam("pageSize") @Valid Integer pageSize,
            @RequestParam("filter") @Valid String filter
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.getTasksPaginationByFolderId(folderId, token, offset, pageSize, filter));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error getting tasks");
        } catch (EmptyResultDataAccessException e) {
            throw new TaskException("Task not found");
        }  catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/pagination/all")
    public ResponseEntity<ResponseBaseDto> getAllTasks(
            @RequestHeader("Authorization") String token,
            @RequestParam("offset") @Valid Integer offset,
            @RequestParam("pageSize") @Valid Integer pageSize,
            @RequestParam("filter") @Valid String filter
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.getAllTasksPagination(token, offset, pageSize, filter));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error getting tasks");
        } catch (EmptyResultDataAccessException e) {
            throw new TaskException("Task not found");
        }  catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @DeleteMapping("/private/delete/{taskId}")
    public ResponseEntity<ResponseBaseDto> deleteTask(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID taskId
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.deleteTask(taskId));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error deleting task");
        } catch (EmptyResultDataAccessException e) {
            throw new TaskException("Task not found");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @PutMapping("/private/update/{taskId}")
    public ResponseEntity<ResponseBaseDto> updateTask(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID taskId,
            @RequestBody @Valid UpdateTaskDto task
    ) throws TaskException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(taskService.updateTask(taskId, task, token));
        } catch (CannotCreateTransactionException e) {
            throw new TaskException("Error updating task");
        } catch (EmptyResultDataAccessException e) {
            throw new TaskException("Task not found");
        }  catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }
}
