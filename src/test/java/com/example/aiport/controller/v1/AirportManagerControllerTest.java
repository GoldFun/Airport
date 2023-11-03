package com.example.aiport.controller.v1;

import com.example.aiport.dto.AuthDto;
import com.example.aiport.dto.ChangeRoleDto;
import com.example.aiport.dto.RecruitDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.RequestsJobsEntity;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.service.AirportManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AirportManagerControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_changeRole_OK() throws Exception {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setLogin("test");
        changeRoleDto.setRole("ENGINEER");
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/changeRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changeRoleDto)))
                .andExpect(MockMvcResultMatchers.content().string("Вы успешно изменили роль пользователю!"));
    }
    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_changeRole_Exception() throws Exception {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setLogin("test");
        changeRoleDto.setRole("ENGINEER");
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/changeRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changeRoleDto)));
    }

    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_retire_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/manager/retire").param("username", "test"))
                .andExpect(MockMvcResultMatchers.content().string("Вы успешно уволили своего работника!"));
    }
    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_retire_Exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/manager/retire").param("username", "test"));
    }
    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_recruit_OK()throws Exception{
        RecruitDto recruitDto = new RecruitDto();
        recruitDto.setLogin("test1");
        recruitDto.setAnswer("ACCEPT");
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/changeRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(recruitDto)))
                .andExpect(MockMvcResultMatchers.content().string("Вы успешно изменили роль пользователю!"));
    }
    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_recruit_Exception()throws Exception{
        RecruitDto recruitDto = new RecruitDto();
        recruitDto.setLogin("test1");
        recruitDto.setAnswer("ACCEPT");
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/recruit").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(recruitDto)));
    }
    @Test
    @WithMockUser(username = "RCV", roles = "MANAGER_AIRPORT")
    public void test_reading_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/manager/reading"))
                .andExpect(MockMvcResultMatchers.content().string("Отсчёт отправлен!"));
    }
}
