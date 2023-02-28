package br.com.todo.api.modules.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Folder id is required")
    @UUID(message = "Folder need to be a valid UUID")
    private String folderId;
}
