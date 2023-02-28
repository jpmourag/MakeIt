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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author jpmgo
 */
@Named("taskLV")
@ApplicationScoped
public class TaskLazyView implements Serializable {

    private LazyDataModel<Task> lazyModel;

}
