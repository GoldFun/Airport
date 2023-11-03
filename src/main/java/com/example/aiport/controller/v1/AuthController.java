package com.example.aiport.controller.v1;

import com.example.aiport.dto.AuthDto;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService noAuthService) {
        this.authService = noAuthService;
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody AuthDto authDtoSurname) throws InvalidLoginException {
            System.out.println(authService);
            String login = authService.register(authDtoSurname.getLogin(),authDtoSurname.getPassword(),authDtoSurname.getSurname());
            return "Поздравляю " + login + "! Вы зарегестрировались!!!";
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Вы можете написать register чтобы зарегестрироваться!";
    }


}
