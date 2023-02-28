package br.com.todo.api.modules.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderPaginationDto {
        private String id;
        private String title;
        private Long allTasks;
        private Long allCompletedTasks;
        private Long allUncompletedTasks;
        private Timestamp createdAt;
}
