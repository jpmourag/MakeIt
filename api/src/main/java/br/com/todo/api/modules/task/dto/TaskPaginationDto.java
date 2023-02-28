package br.com.todo.api.modules.task.dto;

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
public class TaskPaginationDto {
    private UUID id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private String folderName;
    private UUID folderId;
    private Timestamp createdAt;
}
