package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;
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
public class EngineerControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "STEW", roles = "ENGINEER")
    public void test_techReview_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/techReview").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы успешно сделали Технический осмотр!"));
    }
    @Test
    @WithMockUser(username = "STEW", roles = "ENGINEER")
    public void test_repair_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/repair").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Самолет успешно отремонтирован!"));
    }
    @Test
    @WithMockUser(username = "STEW", roles = "ENGINEER")
    public void test_refuel_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/refuel").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Инженер залили в бензин самолет"));
    }

}