/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.models;

import br.com.webapp.todo.entities.Task;
import br.com.webapp.todo.services.TaskService;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Data;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author jpmgo
 */
@Data
@Named
@ViewScoped
public class LazyTaskDataModel extends LazyDataModel<Task> {

    private static final long serialVersionUID = 1L;

    private TaskService service = new TaskService();
    private int total;
    private List<Task> datasource;

    private Map<String, String> params;

    public LazyTaskDataModel(List<Task> datasource, int total, Map<String, String> params) {
        this.datasource = datasource;
        this.total = total;
        this.params = params;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return total;
    }

    @Override
    public List<Task> load(int offset, int pageSize,
            Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

        List<Task> tasks = service.getTasks(offset, pageSize, params);
        setRowCount(service.getTotal());

        return tasks;
    }
}
