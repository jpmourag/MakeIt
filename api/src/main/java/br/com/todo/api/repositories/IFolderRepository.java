package br.com.todo.api.repositories;

import br.com.todo.api.entities.Folder;
import br.com.todo.api.modules.folder.dto.FilteredFolderDataDto;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IFolderRepository extends JpaRepository<Folder, UUID> {
}
