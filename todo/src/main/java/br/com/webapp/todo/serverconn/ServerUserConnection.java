/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.serverconn;

import br.com.webapp.todo.dto.ResponseBaseDto;
import java.util.HashMap;
import java.util.Map;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

/**
 *
 * @author jpmgo
 */
public class ServerUserConnection extends ServerConnection {

    public String login(String email, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return post(body, "/user/public/login");
    }

    public String register(Map<String, Object> body) {
        return post(body, "/user/public/create");
    }

    public Boolean isUserAuthenticated() {
        try {
            var httpclient = HttpClients.createDefault();
            var httpGet = ClassicRequestBuilder
                    .get(linkConnection + "/user/private/is/authenticated")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAuthToken())
                    .build();

            var json = responseToJson(httpclient, httpGet);
            var data = gson.fromJson(json, ResponseBaseDto.class);
            return data.getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserData() {
        try {
            var httpclient = HttpClients.createDefault();
            var httpGet = ClassicRequestBuilder
                    .get(linkConnection + "/user/private/get/data")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getAuthToken())
                    .build();

            var json = responseToJson(httpclient, httpGet);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public String updateUser(Map<String, Object> body) {
        return put(body, "/user/private/update");
    }

    public String deleteUser() {
        return delete("/user/private/delete");
    }
}
