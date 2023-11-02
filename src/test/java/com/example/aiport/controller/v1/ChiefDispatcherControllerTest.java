package com.example.aiport.controller.v1;

import com.example.aiport.dto.ConfirmDto;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.FailConfirmException;
import com.example.aiport.exception.FlyException;
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
public class ChiefDispatcherControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirm_OK() throws Exception{
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirm").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)))
                .andExpect(MockMvcResultMatchers.content().string("самолет зарегестрирован!"));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirm_Exception() throws Exception{
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirm").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmFlights_OK() throws Exception {
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmFlights").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)))
                .andExpect(MockMvcResultMatchers.content().string("рейс зарегестрирован!"));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmFlights_Exception() throws Exception {
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmFlights").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmTakeFly_OK() throws Exception {
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmTakeFly").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)))
                .andExpect(MockMvcResultMatchers.content().string("ответ передан пилоту и самолет скоро взлетит!"));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmTakeFly_Exception() throws Exception {
        ConfirmDto confirmDto = new ConfirmDto();
        confirmDto.setAnswer("ACCEPT");
        confirmDto.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmTakeFly").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(confirmDto)));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmAdoption_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmAdoption").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы потвердили посадку самолета"));
    }
    @Test
    @WithMockUser(username = "Q", roles = "CHIEF_DISPATCHER")
    public void test_confirmAdoption_Exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/chiefDispatchers/confirmAdoption").param("id","1"));
    }


}