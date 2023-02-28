/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.views;

import br.com.webapp.todo.entities.Folder;
import br.com.webapp.todo.entities.Task;
import br.com.webapp.todo.models.LazyTaskDataModel;
import br.com.webapp.todo.services.TaskService;
import br.com.webapp.todo.utils.WebComunication;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author jpmgo
 */
@Data
@Named("taskLV")
@ApplicationScoped
public class TaskLazyView implements Serializable {

    private LazyDataModel<Task> lazyModel;

    @Inject
    private TaskService taskService;

    @PostConstruct
    public void init() {
        var folderId = WebComunication.getDataFromParams("folderId");
        var filter = WebComunication.getDataFromParams("filter");
        var search = WebComunication.getDataFromParams("search");
        Map<String, String> params = new HashMap<>();
        params.put("folderId", folderId);
        params.put("filter", filter);
        params.put("search", search);

        if (filter != null || search != null) {
            if (folderId != null && folderId.equals("none")) {
                var folder = Folder.builder().id("none").title("Resultados").build();
                taskService.setCurrentFolder(folder);
            }
            lazyModel = new LazyTaskDataModel(taskService.getTasks(0, 6, params),
                    taskService.getTotal(), params);
        }
    }

    public void redirectSearchPage(String search) {
        Map<String, String> params = new HashMap<>();
        params.put("folderId", "none");
        params.put("filter", "");
        params.put("search", search);

        var folder = Folder.builder().id("none").title("Resultados").build();
        taskService.setCurrentFolder(folder);

        WebComunication.redirectWithParameters("tasks", params);
        lazyModel = new LazyTaskDataModel(taskService.getTasks(0, 6, params), taskService.getTotal(), params);
    }

    public void redirectToFolderPageByFolderId(String folderId, String folderName) {
//        var folder = folderService.getFolderById(folderId);
        var folder = Folder.builder().id(folderId).title(folderName).build();
        if (folder == null) {
            return;
        }
        redirect(folder, "all", "");
    }

    public void redirect(Folder folder, String filter, String search) {
        Map<String, String> params = new HashMap<>();
        params.put("folderId", folder.getId());
        params.put("filter", filter);
        params.put("search", search);

        taskService.setCurrentFolder(folder);

        WebComunication.redirectWithParameters("tasks", params);
        lazyModel = new LazyTaskDataModel(taskService.getTasks(0, 6, params), taskService.getTotal(), params);
    }

    public void redirectAllTasks(String filter) {
        Map<String, String> menu = new HashMap<>();
        menu.put("all", "Todas");
        menu.put("completed", "Concluídas");
        menu.put("uncompleted", "Não Concluídas");

        Map<String, String> params = new HashMap<>();
        params.put("folderId", "none");
        params.put("filter", filter);
        params.put("search", "");

        var folder = Folder.builder()
                .id("none")
                .title(menu.get(filter))
                .build();
        taskService.setCurrentFolder(folder);

        WebComunication.redirectWithParameters("tasks", params);
        lazyModel = new LazyTaskDataModel(taskService.getTasks(0, 6, params), taskService.getTotal(), params);
    }

    public void redirect(String filter) {
        Map<String, String> newParams = new HashMap<>();

        var folder = taskService.getCurrentFolder();

        newParams.put("folderId", String.valueOf(folder.getId()));
        newParams.put("filter", String.valueOf(filter));
        newParams.put("search", "");

        WebComunication.redirectWithParameters("tasks", newParams);
        lazyModel = new LazyTaskDataModel(taskService.getTasks(0, 6, newParams),
                taskService.getTotal(), newParams);
    }
}
