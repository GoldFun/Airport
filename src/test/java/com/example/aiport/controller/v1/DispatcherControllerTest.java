package com.example.aiport.controller.v1;

import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.PlanesDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.exception.FailSendException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidFlightsException;
import com.example.aiport.exception.InvalidPlaneException;
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
public class DispatcherControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_createPlane_OK() throws Exception{
        PlanesDto planesDto = new PlanesDto();
        planesDto.setMarka("marka");
        planesDto.setModel("model");
        planesDto.setCountPlace(5);
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/createPlane").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(planesDto)))
            .andExpect(MockMvcResultMatchers.content().string("Запрос отправлен Главному инженеру и в скорем времени он возьмется за дело!"));
    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_send_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/send").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы отправили самолет на потверждение Главному диспетчеру!"));

    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_send_Exception() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/send").param("id","1"));

    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_createFlights_OK() throws Exception{
        FlightsDto flightsDto = new FlightsDto();
        flightsDto.setTitle("Title");
        flightsDto.setPlaceOfDeparture(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/createFlights").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(flightsDto)))
                .andExpect(MockMvcResultMatchers.content().string("Рейс создан!"));

    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_createFlights_Exception() throws Exception{
        FlightsDto flightsDto = new FlightsDto();
        flightsDto.setTitle("Title");
        flightsDto.setPlaceOfDeparture(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/createFlights").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(flightsDto)));
    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_takeOffFly_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/takeOffFly").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы отправили рейс на потверждение отправки Главного диспетчера!"));
    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_takeOffFly_Exception() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/takeOffFly").param("id","1"));
    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_adoption_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/adoption").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы отправили рейс на потверждение отправки Главного диспетчера!"));
    }
    @Test
    @WithMockUser(username = "STE", roles = "DISPATCHER")
    public void test_adoption_Exception() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/dispatcher/adoption").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("Вы отправили рейс на потверждение отправки Главного диспетчера!"));
    }
}