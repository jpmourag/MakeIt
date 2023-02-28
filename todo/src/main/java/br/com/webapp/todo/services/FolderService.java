/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.services;

import br.com.webapp.todo.dto.*;
import br.com.webapp.todo.entities.Folder;
import br.com.webapp.todo.serverconn.ServerFolderConnection;
import br.com.webapp.todo.utils.ExtraForView;
import br.com.webapp.todo.utils.HandleDate;
import br.com.webapp.todo.utils.WebComunication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lombok.Data;

/**
 *
 * @author jpmgo
 */
@Data
@Named
@ApplicationScoped
public class FolderService implements Serializable {

    private String title;
    private Integer total = 0;
    private List<Folder> foldersList;

    private ServerFolderConnection serverFolderConnection = new ServerFolderConnection();
    private Gson gson = new Gson();

    public List<Folder> getFolders(int offset, int pageSize) {
        List<Folder> folders = new ArrayList<>();
        var json = serverFolderConnection.folderPagination(offset, pageSize);

        var folderPaginationResponseType
                = new TypeToken<ResponseBaseDto<PaginationBaseDto<List<Folder>>>>() {
                }.getType();

        ResponseBaseDto<PaginationBaseDto<List<Folder>>> folderPaginationResponse
                = gson.fromJson(json, folderPaginationResponseType);

        if (folderPaginationResponse.getStatusCode() == 200) {
            total = folderPaginationResponse.getData().getTotal();
            return folderPaginationResponse.getData().getData()
                    .stream().map(f -> {
                        f.setCreatedAt(HandleDate.formartStringUTCDate(f.getCreatedAt()));
                        return f;
                    }).collect(Collectors.toList());
        }
        return folders;
    }

    public Folder getFolderById(String folderId) {
        if (folderId.isBlank()) {
            ExtraForView.triggerErrorMessage("Um error ocorreu.");
            return null;
        }

        var json = serverFolderConnection.getFolderById(folderId);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Um error ocorreu.");
            return null;
        }

        var folderResponseType = new TypeToken<ResponseBaseDto<Folder>>() {
        }.getType();
        ResponseBaseDto<Folder> folderResponse = gson.fromJson(json, folderResponseType);

        if (folderResponse.getStatusCode() == 200) {
            return folderResponse.getData();
        }
        ExtraForView.triggerErrorMessage("Um error ocorreu.");
        return null;
    }

    public void createFolder() {
        if (title.isBlank()) {
            ExtraForView.triggerWarnMessage("Preencha todos os campo corretamente");
            return;
        }
        var body = new HashMap<String, Object>();
        body.put("title", title);
        var json = serverFolderConnection.createFolder(body);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não foi possível criar sua pasta.");
            return;
        }
        ResponseBaseDto response = gson.fromJson(json, ResponseBaseDto.class);
        if (response.getStatusCode() == 201) {
            WebComunication.redirect("home");
            return;
        }
        ExtraForView.triggerErrorMessage("Não foi possível criar sua pasta.");
    }

    public void updateFolder(String title, String folderId) {
        if (title.isBlank()) {
            ExtraForView.triggerWarnMessage("Preencha todos os campo corretamente");
            return;
        }
        var body = new HashMap<String, Object>();
        body.put("title", title);
        var json = serverFolderConnection.updateFolder(body, folderId);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não foi possível atualizar sua pasta.");
            return;
        }
        ResponseBaseDto response = gson.fromJson(json, ResponseBaseDto.class);
        if (response.getStatusCode() == 200) {
            ExtraForView.updateComponent("gridData_folders");
            return;
        }
        ExtraForView.triggerErrorMessage("Não foi possível atualizar sua pasta.");
    }

    public void deleteFolder(String folderId) {
        var json = serverFolderConnection.deleteFolder(folderId);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não foi possível remover sua pasta.");
            return;
        }
        ResponseBaseDto response = gson.fromJson(json, ResponseBaseDto.class);
        if (response.getStatusCode() == 200) {
            ExtraForView.updateComponent("gridData_folders");
            return;
        }
        ExtraForView.triggerErrorMessage("Não foi possível remover sua pasta.");
    }

    public String generateRandomColor() {
        return ExtraForView.generateRandomColor();
    }
}
