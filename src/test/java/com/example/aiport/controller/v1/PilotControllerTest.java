package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.ReviewsDtoWithUsers;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;
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
public class PilotControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "STEWA", roles = "PILOT")
    public void test_takeOff_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/takeOff"))
                .andExpect(MockMvcResultMatchers.content().string("Самолет взлетел"));
    }
    @Test
    @WithMockUser(username = "STEWA", roles = "PILOT")
    public void test_planeLanding_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/planeLanding"))
                .andExpect(MockMvcResultMatchers.content().string("Запрос диспечтером из другого Аэротра отправлен, ждем разрешение на посадку!"));
    }
    @Test
    @WithMockUser(username = "STEWA", roles = "PILOT")
    public void test_landing_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/planeLanding"))
                .andExpect(MockMvcResultMatchers.content().string("Рейс прошел успешно и вы смогли долететь до другого Аэрпотра!"));
    }
    @Test
    @WithMockUser(username = "STEWA", roles = "PILOT")
    public void test_wantPlane_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/wantPlane"))
                .andExpect(MockMvcResultMatchers.content().string("Вы получили в распоряжении самолет!"));
    }
    @Test
    @WithMockUser(username = "STEWA", roles = "PILOT")
    public void test_wantPlane_Exception() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/engineer/wantPlane"));
    }
}