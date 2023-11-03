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
public class ChiefStewardControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "S", roles = "CHIEF_ENGINEER")
    public void test_scheduleBriefing_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefSteward/scheduleBriefing"))
                .andExpect(MockMvcResultMatchers.content().string("вы назначили стюардам инструктаж!"));
    }
    @Test
    @WithMockUser(username = "S", roles = "CHIEF_ENGINEER")
    public void test_scheduleGiveFood_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefSteward/scheduleGiveFood"))
                .andExpect(MockMvcResultMatchers.content().string("вы назначили стюардам раздачу еды!"));
    }
    @Test
    @WithMockUser(username = "S", roles = "CHIEF_ENGINEER")
    public void test_wantPlane_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefSteward/wantPlane").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы получили в распоряжении самолет!"));
    }



}