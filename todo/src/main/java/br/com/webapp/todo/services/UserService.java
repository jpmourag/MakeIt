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

    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void register() {
        if (!isValidRegister()) {
            return;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("email", email);
        body.put("password", password);

        var json = serverUserConnection.register(body);
        if (json == null) {
            ExtraForView.triggerWarnMessage("Não foi possível criar a conta");
            return;
        }

        var registerResponseType = new TypeToken<ResponseBaseDto>() {
        }.getType();
        ResponseBaseDto registerResponse = gson.fromJson(json, registerResponseType);

        if (registerResponse.getStatusCode() == 201) {
            WebComunication.redirect("index");
            return;
        }

        if (registerResponse.getMessage() != null) {
            var messageToShow = "Não foi possível criar conta";
            if (registerResponse.getMessage().equals("User already exists")) {
                messageToShow = "Email já foi utilizado";
            }
            ExtraForView.triggerErrorMessage(messageToShow);
            return;
        }
        ExtraForView.triggerErrorMessage("Não foi possível criar conta");
    }

    public void login() {
        if (email.isBlank() || password.isBlank()) {
            ExtraForView.triggerWarnMessage("Preencha todos os campos corretamente");
            return;
        }
        var json = serverUserConnection.login(email, password);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não foi possível fazer login");
            return;
        }
        var loginResponseType = new TypeToken<ResponseBaseDto<LoginResponseDto>>() {
        }.getType();
        ResponseBaseDto<LoginResponseDto> loginResponse = gson.fromJson(json, loginResponseType);
        if (loginResponse.getStatusCode() == 200) {
            persistentDataHandler.save("token", loginResponse.getData().getToken());
            WebComunication.redirect("home");
            return;
        }
        ExtraForView.triggerErrorMessage("Email ou senha errados");
    }

    public void logout() {
        persistentDataHandler.remove("token");
        WebComunication.redirect("index");
    }

    private void getUserData() {
        var json = serverUserConnection.getUserData();
        if (json == null) {
            return;
        }
        var userDataResponseType = new TypeToken<ResponseBaseDto<UserData>>() {
        }.getType();

        ResponseBaseDto<UserData> userDataResponse = gson.fromJson(json, userDataResponseType);
        if (userDataResponse.getStatusCode() == 200) {
            var data = userDataResponse.getData();
            name = data.getName();
            email = data.getEmail();
        }

    }

    public void updateUser() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        var json = serverUserConnection.updateUser(body);
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não atualizar a conta");
            return;
        }
        ResponseBaseDto response = gson.fromJson(json, ResponseBaseDto.class);
        if (response.getStatusCode() == 200) {
            ExtraForView.triggerInfoMessage("Conta Atualiada");
            return;
        }
        ExtraForView.triggerWarnMessage("Não foi possível atualizar a conta");
    }

    public void deleteUser() {
        var json = serverUserConnection.deleteUser();
        if (json == null) {
            ExtraForView.triggerErrorMessage("Não foi possível apagar a conta");
            return;
        }
        ResponseBaseDto response = gson.fromJson(json, ResponseBaseDto.class);
        if (response.getStatusCode() == 200) {
            logout();
        }
        ExtraForView.triggerErrorMessage("Não foi possível apagar a conta");
    }
}
