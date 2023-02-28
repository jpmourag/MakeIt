/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.models;

import br.com.webapp.todo.entities.Folder;
import br.com.webapp.todo.services.FolderService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author jpmgo
 */
public class LazyFolderDataModel extends LazyDataModel<Folder> {

    private static final long serialVersionUID = 1L;

    private List<Folder> datasource;
    private int amountOfFolders;

    private FolderService folderService = new FolderService();

    public LazyFolderDataModel(List<Folder> datasource, int amountOfFolders) {
        this.datasource = datasource;
        this.amountOfFolders = amountOfFolders;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return amountOfFolders;
    }

    @Override
    public List<Folder> load(int offset, int pageSize, 
            Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<Folder> folders = folderService.getFolders(offset, pageSize);
        return folders;
    }
}
