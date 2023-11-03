package com.example.aiport.controller.v1;

import com.example.aiport.dto.GiveOrderDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidRequestException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ChiefEngineerControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "QI", roles = "CHIEF_ENGINEER")
    public void test_giveOrder_OK() throws Exception{
        GiveOrderDto giveOrderDto = new GiveOrderDto();
        giveOrderDto.setId(1L);
        giveOrderDto.setPlaneStatus("TECH_REVIEW");
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefEngineer/giveOrder").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(giveOrderDto)))
                .andExpect(MockMvcResultMatchers.content().string("Вы отдали приказ Инженерам и в скорем времени они займутся за дело!"));
    }
    @Test
    @WithMockUser(username = "QI", roles = "CHIEF_ENGINEER")
    public void test_giveOrder_Exception() throws Exception{
        GiveOrderDto giveOrderDto = new GiveOrderDto();
        giveOrderDto.setId(1L);
        giveOrderDto.setPlaneStatus("TECH_REVIEW");
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefEngineer/giveOrder").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(giveOrderDto)));

    }

}