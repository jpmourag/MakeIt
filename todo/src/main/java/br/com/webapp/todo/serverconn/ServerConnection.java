/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.serverconn;

import br.com.webapp.todo.config.Env;
import br.com.webapp.todo.utils.PersistentDataHandler;
import com.google.gson.Gson;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

/**
 *
 * @author jpmgo
 */
public class ServerConnection {

    final protected Gson gson = new Gson();
    final protected String host = Env.get("TODO_SERVER_HOST", "127.0.0.1");
    final protected String port = Env.get("TODO_SERVER_PORT", "3333");
    final protected String linkConnection = "http://" + this.host + ":" + this.port + "/api/v1";

    protected String getAuthToken() {
        return "Bearer " + new PersistentDataHandler().read("token");
    }
}
