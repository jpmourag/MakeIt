/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.services;

import br.com.webapp.todo.utils.PersistentDataHandler;
import br.com.webapp.todo.serverconn.ServerUserConnection;
import br.com.webapp.todo.utils.WebComunication;
import java.io.Serializable;
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
public class PageManagerService implements Serializable {
    
    private final ServerUserConnection serverConnection = new ServerUserConnection();

    public void index(String authenticatedRedirectTo, String notAuthenticatedRedirectTo) {
        var persistentDataHandler = new PersistentDataHandler();
        String takenToken = persistentDataHandler.read("token");
        if (takenToken != null) {
            var isUserAuthenticated = serverConnection.isUserAuthenticated();
            if (isUserAuthenticated) {
                WebComunication.redirect(authenticatedRedirectTo);
            } else {
                WebComunication.redirect(notAuthenticatedRedirectTo);
            }
        } else {
            WebComunication.redirect(notAuthenticatedRedirectTo);
        }
    }
}
