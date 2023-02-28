package br.com.todo.api.modules.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilteredFolderDataDto {
    private UUID id;
    private String title;
    private UUID userId;
    private String userEmail;
    private Timestamp createdAt;
}
