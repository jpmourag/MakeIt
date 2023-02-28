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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folder {

    private String id;
    private String title;
    private int allTasks;
    private int allCompletedTasks;
    private int allUncompletedTasks;
    private String createdAt;
}
