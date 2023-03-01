package br.com.todo.api.repositories;

import br.com.todo.api.entities.Folder;
import br.com.todo.api.modules.folder.dto.FilteredFolderDataDto;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IFolderRepository extends JpaRepository<Folder, UUID> {
    @Query("SELECT COUNT(*) FROM FOLDER f WHERE f.user.email = :email")
    Integer getAmountOfAllFolders(String email);

    @Query("SELECT f FROM FOLDER f WHERE f.user.email = :email ORDER BY f.createdAt DESC")
    List<Folder> findAllByUserEmail(String email, AbstractPageRequest pageable);

    @Query("SELECT new br.com.todo.api.modules.folder.dto." +
            "FilteredFolderDataDto(f.id, f.title, f.user.id, f.user.email, f.createdAt) " +
            "FROM FOLDER f WHERE f.user.email = :email ORDER BY f.createdAt DESC")
    List<FilteredFolderDataDto> findAllFoldersByEmail(String email);
}
