package br.com.todo.api.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBaseDto <T> {
    private int statusCode;
    private String message;
    final private String timestamp = OffsetDateTime.now().toString();
    private T data;
}
