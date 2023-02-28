/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.services;

import br.com.webapp.todo.dto.PaginationBaseDto;
import br.com.webapp.todo.dto.ResponseBaseDto;
import br.com.webapp.todo.dto.folder.FolderName;
import br.com.webapp.todo.entities.Folder;
import br.com.webapp.todo.entities.Task;
import br.com.webapp.todo.serverconn.ServerFolderConnection;
import br.com.webapp.todo.serverconn.ServerTaskConnection;
import br.com.webapp.todo.utils.ExtraForView;
import br.com.webapp.todo.utils.PersistentDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class TaskService implements Serializable {

    private String title;
    private String description;
    private Boolean isCompleted;

    private List<FolderName> foldersToSelect = new ArrayList<>();
    private String folderIdToCreateTask;

    private int total;

    private Folder currentFolder;

    private String searchedWord;
    private List<Task> foundSearchData;

    final private ServerTaskConnection serverTaskConnection = new ServerTaskConnection();
    final private Gson gson = new Gson();
    final private PersistentDataHandler persistentDataHandler = new PersistentDataHandler();

    public List<Task> getTasks(int offset, int pageSize, Map<String, String> params) {
        var folderId = params.get("folderId");
        var filter = params.get("filter");
        var search = params.get("search");

        if (search != null && !search.isBlank() && !search.isEmpty()) {
            return searchTasks(offset, pageSize, search);
        }

        List<Task> tasks = new ArrayList<>();
        String json = null;
        if (folderId == null || folderId.isBlank() || folderId.isEmpty() || folderId.equals("none")) {
            json = serverTaskConnection.getAllTasksPagination(offset, pageSize, filter);
        } else {
            json = serverTaskConnection.getTasksPaginationByFolderId(offset, pageSize, filter, folderId);
        }
        if (json == null) {
            return tasks;
        }

        var tasksListResponseType = new TypeToken<ResponseBaseDto<PaginationBaseDto<List<Task>>>>() {
        }.getType();

        ResponseBaseDto<PaginationBaseDto<List<Task>>> tasksListResponse = gson.fromJson(json, tasksListResponseType);

        if (tasksListResponse.getStatusCode() == 200) {
            total = tasksListResponse.getData().getTotal();
            tasks = tasksListResponse.getData().getData();
        }
        return tasks;
    }

    private List<FolderName> getFoldersNames() {
        List<FolderName> res = new ArrayList<>();
        var json = new ServerFolderConnection().getFoldersNames();
        if (json == null) {
            return res;
        }
        var foldersNameResponseType = new TypeToken<ResponseBaseDto<List<FolderName>>>() {
        }.getType();
        ResponseBaseDto<List<FolderName>> foldersNameResponse = gson.fromJson(json, foldersNameResponseType);
        if (foldersNameResponse.getStatusCode() == 200) {
            res = foldersNameResponse.getData();
        }
        return res;
    }
}
