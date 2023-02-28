/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jpmgo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    private String id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private String folderName;
    private String folderId;
    private String createdAt;
}
