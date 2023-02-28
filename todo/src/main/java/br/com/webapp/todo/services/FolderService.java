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
}
