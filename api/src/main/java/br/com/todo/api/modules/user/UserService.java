package br.com.todo.api.modules.user;

import br.com.todo.api.entities.User;
import br.com.todo.api.exceptions.throwables.entity.UserException;
import br.com.todo.api.global.dto.ResponseBaseDto;
import br.com.todo.api.jwt.JwtService;
import br.com.todo.api.modules.user.dto.AuthRequestDto;
import br.com.todo.api.modules.user.dto.CreateUserDto;
import br.com.todo.api.modules.user.dto.UpdateUserDto;
import br.com.todo.api.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public ResponseBaseDto createUser(CreateUserDto userToCreate) {
        var user = User.builder()
                .name(userToCreate.getName())
                .email(userToCreate.getEmail())
                .password(passwordEncoder.encode(userToCreate.getPassword()))
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        Map<String, String> data = new HashMap<>();
        data.put("token", jwtToken);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(data)
                .message("User created")
                .build();
    }

    public ResponseBaseDto login(AuthRequestDto request) throws UserException {
        var user = findByEmail(request.getEmail());
        if (user != null) {
            var isPasswordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if (isPasswordCorrect) {
                var jwtToken = jwtService.generateToken(user);
                Map<String, String> data = new HashMap<>();
                data.put("token", jwtToken);
                return ResponseBaseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(data)
                        .message("User logged in")
                        .build();
            }
        }
        throw new UserException("Email or password incorrect");
    }

    public ResponseBaseDto getUserData(String token) {
        var email = getEmailFromToken(token);
        var data = userRepository.getUserByEmail(email);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .message("User data")
                .build();
    }

    public ResponseBaseDto isAuthenticated(String token) {
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .data(true)
                .message("User is authenticated")
                .build();
    }

    @Transactional
    public ResponseBaseDto deleteUser(String token) {
        var email = getEmailFromToken(token);
        userRepository.deleteByEmail(email);
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User deleted")
                .build();
    }

    @Transactional
    public ResponseBaseDto updateUser(UpdateUserDto user, String token) throws UserException {
        var email = getEmailFromToken(token);
        System.out.println(user + " " + token + " " + email);
        userRepository.saveUserByEmail(email, user.getName());
        return ResponseBaseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User updated")
                .build();
    }

    // Fun????es auxiliares abaixo sem response
    public String getEmailFromToken(String token) {
        var jwt = token.replace("Bearer ", "");
        return jwtService.extractUsername(jwt);
    }

    public User getUserFromToken(String token) {
        var email = getEmailFromToken(token);
        return findByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
