package br.com.todo.api.modules.folder;

import br.com.todo.api.entities.Folder;
import br.com.todo.api.exceptions.throwables.entity.FolderException;
import br.com.todo.api.global.dto.PaginationBaseResponseDto;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.modules.folder.dto.*;
import br.com.todo.api.modules.user.UserService;
import br.com.todo.api.repositories.IFolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

// todo
// # criar/pegar/deletar/atualizar
// # pegar quantidade total de folder q o user tem
// # pegar range de folder pelo id do user - pagination
// # quantityOfTasks, quantityOfCompletedTasks, quantityOfUncompletedTasks

@Service
public class FolderService {
    @Autowired
    private IFolderRepository folderRepository;
    @Autowired
    private UserService userService;


    public ResponseBaseDto deleteFolder(UUID folderId) {
        folderRepository.deleteById(folderId);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Folder deleted")
                .build();
    }


    // Funções auxiliares abaixo sem response
    public List<FilteredFolderDataDto> getAllFolders(String token) {
        var email = userService.getEmailFromToken(token);
        return folderRepository.findAllFoldersByEmail(email);
    }
    
    public Folder getFolderById(UUID folderId, String token) {
        var email = userService.getEmailFromToken(token);
        return folderRepository.findFolderByIdAndUserEmail(folderId, email);
    }
}
