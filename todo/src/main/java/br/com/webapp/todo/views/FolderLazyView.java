/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.views;

import br.com.webapp.todo.entities.Folder;
import br.com.webapp.todo.models.LazyFolderDataModel;
import br.com.webapp.todo.services.FolderService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author jpmgo
 */
@Data
@Named("folderLV")
@ViewScoped
public class FolderLazyView implements Serializable {

    private LazyDataModel<Folder> lazyModel;

    private Folder selectedFolder;

    @Inject
    private FolderService service;

    @PostConstruct
    public void init() {
        lazyModel = new LazyFolderDataModel(service.getFolders(0, 6), service.getTotal());
    }
}
