package br.com.todo.api.modules.user;

import br.com.todo.api.exceptions.CustomResponseEntityExceptionHandler;
import br.com.todo.api.exceptions.throwables.entity.EntityException;
import br.com.todo.api.exceptions.throwables.entity.UserException;
import br.com.todo.api.exceptions.throwables.global.UnmappedErrorException;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.modules.user.dto.AuthRequestDto;
import br.com.todo.api.modules.user.dto.CreateUserDto;
import br.com.todo.api.modules.user.dto.UpdateUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/public/create")
    public ResponseEntity<ResponseBaseDto> create(
            @RequestBody @Valid CreateUserDto user
    ) throws UserException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (DataIntegrityViolationException e) {
            throw new UserException("User already exists");
        } catch (CannotCreateTransactionException e) {
            throw new UserException("Error creating user");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }
}
