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

    public ResponseBaseDto getFoldersPagination(String token, int offset, int pageSize) {
        var email = userService.getEmailFromToken(token);
        List<FolderPaginationDto> folders = new ArrayList<>();
        folderRepository.findAllByUserEmail(email, PageRequest.of(offset, pageSize))
                .forEach(f -> {
                            var allTasks = ((Integer) f.getTasks().size()).longValue();
                            var allCompletedTasks = f.getTasks().stream().filter(t -> t.getIsCompleted().equals(true)).count();
                            var allUncompletedTasks = allTasks - allCompletedTasks;
                            var folder = FolderPaginationDto.builder()
                                    .id(f.getId().toString())
                                    .title(f.getTitle())
                                    .allTasks(allTasks)
                                    .allCompletedTasks(allCompletedTasks)
                                    .allUncompletedTasks(allUncompletedTasks)
                                    .createdAt(f.getCreatedAt())
                                    .build();
                            folders.add(folder);
                        });


        PaginationBaseResponseDto res = PaginationBaseResponseDto.builder()
                .data(folders)
                .total(folderRepository.getAmountOfAllFolders(email))
                .build();

        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .data(res)
                .message("Folders gotten")
                .build();
    }

    public ResponseBaseDto getAllFoldersTitle(String token) {
        var folder = this.getAllFolders(token)
                .stream().map(f -> {
                    return FolderTitleDto.builder()
                            .id(f.getId().toString())
                            .title(f.getTitle())
                            .build();
                });
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .data(folder)
                .message("Folder gotten")
                .build();
    }

    public ResponseBaseDto updateFolder(UUID folderId, UpdateFolderDto folderNewData) throws FolderException {
        var folder = folderRepository.findById(folderId).orElse(null);
        if (folder != null) {
            folder.setTitle(folderNewData.getTitle());
            var updatedFolder = folderRepository.save(folder);
            var filteredUpdatedFolder = FilteredFolderDataDto.builder()
                    .id(updatedFolder.getId())
                    .title(updatedFolder.getTitle())
                    .userId(updatedFolder.getUser().getId())
                    .userEmail(updatedFolder.getUser().getEmail())
                    .createdAt(updatedFolder.getCreatedAt())
                    .build();
            return ResponseBaseDto.builder()
                    .statusCode(HttpStatus.OK.value())
                    .data(filteredUpdatedFolder)
                    .message("Folder updated")
                    .build();
        }
        throw new FolderException("Folder not found");
    }

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
