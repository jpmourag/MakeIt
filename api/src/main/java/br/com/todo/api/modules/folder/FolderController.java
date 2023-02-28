package br.com.todo.api.modules.folder;

import br.com.todo.api.exceptions.CustomResponseEntityExceptionHandler;
import br.com.todo.api.exceptions.throwables.entity.EntityException;
import br.com.todo.api.exceptions.throwables.entity.FolderException;
import br.com.todo.api.exceptions.throwables.global.UnmappedErrorException;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.modules.folder.dto.CreateFolderDto;
import br.com.todo.api.modules.folder.dto.UpdateFolderDto;
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
@RequestMapping("/api/v1/folder")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping("/private/create")
    public ResponseEntity<ResponseBaseDto> create(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid CreateFolderDto folder
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.createFolder(folder, token));
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error creating folder");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/get/{folderId}")
    public ResponseEntity<ResponseBaseDto> getFolderById(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID folderId
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.getFolderByIdWithResponse(folderId, token));
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error getting folder");
        } catch (EntityException e){
            throw new FolderException(e.getMessage());
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @PutMapping("/private/update/{folderId}")
    public ResponseEntity<ResponseBaseDto> updateFolder(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID folderId,
            @RequestBody @Valid UpdateFolderDto folderNewData
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.updateFolder(folderId, folderNewData));
        } catch (EntityException e){
            throw new FolderException(e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            throw new FolderException("Folder not found");
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error updating folder");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @DeleteMapping("/private/delete/{folderId}")
    public ResponseEntity<ResponseBaseDto> deleteFolder(
            @RequestHeader("Authorization") String token,
            @PathVariable @Valid UUID folderId
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.deleteFolder(folderId));
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error deleting folder");
        }  catch (EmptyResultDataAccessException e) {
            throw new FolderException("Folder not found");
        }  catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/pagination")
    public ResponseEntity<ResponseBaseDto> getFoldersPagination(
            @RequestHeader("Authorization") String token,
            @RequestParam("offset") @Valid Integer offset,
            @RequestParam("pageSize") @Valid Integer pageSize,
            @RequestParam("filter") @Valid String filter
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.getFoldersPagination(token, offset, pageSize));
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error getting folders pagination");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }

    @GetMapping("/private/get/folders/name")
    public ResponseEntity<ResponseBaseDto> getFolderByName(
            @RequestHeader("Authorization") String token
    ) throws FolderException, UnmappedErrorException {
        try {
            return ResponseEntity.ok(folderService.getAllFoldersTitle(token));
        } catch (CannotCreateTransactionException e) {
            throw new FolderException("Error getting folder by name");
        } catch (Exception e) {
            throw new UnmappedErrorException(e);
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
            ({
                    MethodArgumentNotValidException.class,
                    MissingServletRequestParameterException.class,
                    MethodArgumentTypeMismatchException.class
            })
    public ResponseEntity<ResponseBaseDto> handleValidationExceptions(Exception ex)
            throws UnmappedErrorException {
        return CustomResponseEntityExceptionHandler.handleValidationExceptions(ex);
    }
}
