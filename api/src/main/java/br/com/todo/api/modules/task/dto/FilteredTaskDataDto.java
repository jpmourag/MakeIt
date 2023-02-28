package br.com.todo.api.modules.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilteredTaskDataDto {
    private UUID id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private UUID folderId;
    private String folderTitle;
}
