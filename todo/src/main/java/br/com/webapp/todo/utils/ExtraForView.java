/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.utils;

import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jpmgo
 */
public class ExtraForView {

    static public void triggerErrorMessage(String msg) {
        var message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", msg);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    static public void triggerInfoMessage(String msg) {
        var message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg);;
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    static public void triggerWarnMessage(String msg) {
        var message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", msg);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
