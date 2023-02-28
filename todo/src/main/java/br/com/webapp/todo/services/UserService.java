/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.webapp.todo.services;

import br.com.webapp.todo.dto.ResponseBaseDto;
import br.com.webapp.todo.dto.user.LoginResponseDto;
import br.com.webapp.todo.dto.user.UserData;
import br.com.webapp.todo.serverconn.ServerUserConnection;
import br.com.webapp.todo.utils.ExtraForView;
import br.com.webapp.todo.utils.PersistentDataHandler;
import br.com.webapp.todo.utils.WebComunication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
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
public class UserService implements Serializable {

    // data
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    final private Gson gson = new Gson();
    final private PersistentDataHandler persistentDataHandler = new PersistentDataHandler();
    final private ServerUserConnection serverUserConnection = new ServerUserConnection();

    private String token = persistentDataHandler.read("token");

    @PostConstruct
    public void init() {
        if (token != null && !token.equals("0")) {
            getUserData();
        }
    }

    private Boolean isValidRegister() {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            ExtraForView.triggerWarnMessage("Preencha todos os dados corretamente");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            ExtraForView.triggerWarnMessage("Senhas incompatíveis.");
            return false;
        }
        if (!isValidEmail(email)) {
            ExtraForView.triggerWarnMessage("Digite um email válido");
            return false;
        }
        return true;
    }
}
