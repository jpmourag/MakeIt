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
}
