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
public class ServerFolderConnection extends ServerConnection {

    public String getFolderById(String folderId) {
        try {
            var httpclient = HttpClients.createDefault();
            var httpGet = ClassicRequestBuilder
                    .get(linkConnection + "/folder/private/get/" + folderId)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAuthToken())
                    .build();

            var json = responseToJson(httpclient, httpGet);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public String getFoldersNames() {
        try {
            var httpclient = HttpClients.createDefault();
            var httpGet = ClassicRequestBuilder
                    .get(linkConnection + "/folder/private/get/folders/name")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAuthToken())
                    .build();

            var json = responseToJson(httpclient, httpGet);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public String folderPagination(int offset, int pageSize) {
        return pagination(offset, pageSize, "", "/folder/private/pagination");
    }

    public String createFolder(Map<String, Object> body) {
        return post(body, "/folder/private/create");
    }

    public String updateFolder(Map<String, Object> body, String folderId) {
        return put(body, "/folder/private/update/" + folderId);
    }

    public String deleteFolder(String folderId) {
        return delete("/folder/private/delete/" + folderId);
    }
}
