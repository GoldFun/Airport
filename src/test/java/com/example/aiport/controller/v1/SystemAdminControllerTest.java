package com.example.aiport.controller.v1;

import com.example.aiport.dto.ChangeRoleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SystemAdminControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "123", roles = "SYSTEM_ADMIN")
    public void test_briefing_OK() throws Exception {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setLogin("test");
        changeRoleDto.setRole("ENGINEER");
    mockMvc.perform(MockMvcRequestBuilders.post("/admin/changeRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changeRoleDto)))
                .andExpect(MockMvcResultMatchers.content().string("Вы успешно изменили роль пользователю!"));
    }
    @Test
    @WithMockUser(username = "123", roles = "SYSTEM_ADMIN")
    public void test_briefing_Exception() throws Exception {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setLogin("test");
        changeRoleDto.setRole("ENGINEER");
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/changeRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(changeRoleDto)));
    }
}