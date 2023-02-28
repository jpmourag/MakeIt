/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author jpmgo
 */
public class WebComunication {

    public static String getDataFromParams(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        String taskId = externalContext.getRequestParameterMap().get(key);
        return taskId;
    }

    public static void redirectWithParameters(String pageName, Map<String, String> params) {
        var urlParams = WebComunication.getQueryString(params);
        try {
            var context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(
                    externalContext.getRequestContextPath() + "/" + pageName + ".xhtml" + urlParams);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static String getQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                sb.append(key).append("=").append(value).append("&");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return "?" + sb.toString();
        }

        return "";
    }
}
