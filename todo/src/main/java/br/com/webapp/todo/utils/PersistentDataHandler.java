/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.utils;

import java.util.*;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpmgo
 */
@ApplicationScoped
public class PersistentDataHandler {

    public void save(String key, String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        Cookie cookie = new Cookie(key, data);
        cookie.setMaxAge(3600);
        cookie.setPath(ec.getRequestContextPath());
        response.addCookie(cookie);
    }

    public String read(String key) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        Map<String, Object> cookies = ec.getRequestCookieMap();
        Cookie cookie = (Cookie) cookies.get(key);

        if (cookie != null) {
            String valor = (String) cookie.getValue();
            return valor;
        }
        return null;
    }

    public void remove(String key) {
        save(key, "0");
    }
}
