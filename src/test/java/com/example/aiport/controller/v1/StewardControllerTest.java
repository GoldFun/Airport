package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StewardControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test1", roles = "STEWARD")
    public void test_briefing_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/steward/briefing"))
                .andExpect(MockMvcResultMatchers.content().string("инстуктаж проведен!"));
    }

    @Test
    @WithMockUser(username = "test1", roles = "STEWARD")
    public void test_giveFood_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/steward/briefing"))
                .andExpect(MockMvcResultMatchers.content().string("раздача еды проведена!"));
    }

    @Test
    @WithMockUser(username = "test1", roles = "PILOT")
    public void test_wantPlane_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/steward/wantPlane").param("id", "1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы получили в распоряжении самолет!"));
    }
}
