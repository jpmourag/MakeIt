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

    @PostMapping("/public/login")
    public ResponseEntity<ResponseBaseDto> login(
            @RequestBody @Valid AuthRequestDto request
    ) throws UserException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch (CannotCreateTransactionException e) {
            throw new UserException("Error authenticating user");
        } catch (EntityException e) {
            throw new UserException(e.getMessage());
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/get/data")
    public ResponseEntity<ResponseBaseDto> data(
            @RequestHeader("Authorization") String token
    ) throws UserException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(userService.getUserData(token));
        } catch (UsernameNotFoundException e) {
            throw new UserException("User not found");
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserException("Error to get user data");
        } catch (CannotCreateTransactionException e) {
            throw new UserException("Internal error, can not get user data");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @PutMapping("/private/update")
    public ResponseEntity<ResponseBaseDto> update(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid UpdateUserDto user
    ) throws UserException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(userService.updateUser(user, token));
        } catch (UsernameNotFoundException | EmptyResultDataAccessException e ) {
            throw new UserException("User not found");
        } catch (EntityException e) {
            throw new UserException(e.getMessage());
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<ResponseBaseDto> delete(
            @RequestHeader("Authorization") String token
    ) throws UserException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(userService.deleteUser(token));
        } catch (CannotCreateTransactionException e) {
            throw new UserException("Error to delete user");
        } catch (UsernameNotFoundException | EmptyResultDataAccessException e) {
            throw new UserException("User not found");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }
}
