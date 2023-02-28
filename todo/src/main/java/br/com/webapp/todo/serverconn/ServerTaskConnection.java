/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.serverconn;

import java.util.Map;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

/**
 *
 * @author jpmgo
 */
public class ServerTaskConnection extends ServerConnection {

    public String createTask(Map<String, Object> body) {
        return post(body, "/task/private/create");
    }

    public String getTaskById(String taskId) {
        try {
            var httpclient = HttpClients.createDefault();
            var httpGet = ClassicRequestBuilder
                    .get(linkConnection + "/task/private/get/" + taskId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAuthToken())
                    .build();

            var json = responseToJson(httpclient, httpGet);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public String updateTask(Map<String, Object> body, String taskId) {
        return put(body, "/task/private/update/" + taskId);
    }

    public String deleteTask(String taskId) {
        return delete("/task/private/delete/" + taskId);
    }

    public String getTasksPaginationByFolderId(int offset, int pageSize, String filter, String folderId) {
        return pagination(offset, pageSize, filter, "/task/private/pagination/" + folderId);
    }

    public String getAllTasksPagination(int offset, int pageSize, String filter) {
        return pagination(offset, pageSize, filter, "/task/private/pagination/all");
    }

    public String search(int offset, int pageSize, String filter) {
        return pagination(offset, pageSize, filter, "/task/private/find");
    }
}
