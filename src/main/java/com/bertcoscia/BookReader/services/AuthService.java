package com.bertcoscia.BookReader.services;

import com.bertcoscia.BookReader.dto.requests.LoginRequest;
import com.bertcoscia.BookReader.entities.User;
import com.bertcoscia.BookReader.exceptions.UnauthorizedException;
import com.bertcoscia.BookReader.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JWTTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;

    public String generateToken(LoginRequest request) {
        User user = this.userService.findByEmail(request.email());

        if (bcrypt.matches(request.password(), user.getPassword())) return jwtTools.createToken(user);
        else throw new UnauthorizedException("Wrong password and/or email");
    }
}
