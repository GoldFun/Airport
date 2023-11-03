package com.example.aiport.controller.v1;

import com.example.aiport.dto.AuthDto;
import org.assertj.core.util.diff.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void test_register_OK(){
        try {
            AuthDto authDto = new AuthDto();
            authDto.setLogin("test3");
            authDto.setPassword("test");
            authDto.setSurname("test");

           HttpHeaders headers = new HttpHeaders();
           HttpEntity request = new HttpEntity(authDto,headers);
        URI uri = new URI("http://localhost:"+ port + "/auth/register");
            System.out.println(uri.toString());
            ResponseEntity<String> response = restTemplate.postForEntity(uri.toString(),request,String.class);
            Assertions.assertEquals(response.getBody(),"Поздравляю " + authDto.getLogin() + "! Вы зарегестрировались!!!");
        }catch (Exception e){
            Assertions.fail(e);
        }
    }
}
