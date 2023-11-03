package com.example.aiport.controller.v1;

import com.example.aiport.dto.ApplyJobDto;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.HistoryPastFlightsDto;
import com.example.aiport.dto.ReviewsDto;
import com.example.aiport.exception.*;
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
public class ClientControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_applyJob_OK() throws Exception{
        ApplyJobDto applyJobDto = new ApplyJobDto();
        applyJobDto.setMessage("HELLO");
        applyJobDto.setRole("ENGINEER");
        mockMvc.perform(MockMvcRequestBuilders.post("/client/applyJob").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(applyJobDto)))
                .andExpect(MockMvcResultMatchers.content().string( "Ваш запрос отправлен Менеджеру и в скором времени рассмотрит ваше предложение на работу!"));
    }
    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_applyJob_Exception() throws Exception{
        ApplyJobDto applyJobDto = new ApplyJobDto();
        applyJobDto.setMessage("HELLO");
        applyJobDto.setRole("ENGINEER");
        mockMvc.perform(MockMvcRequestBuilders.post("/client/applyJob").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(applyJobDto)));
    }
    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_registerFlights_OK() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/client/registerFlights").param("id","1"))
                .andExpect(MockMvcResultMatchers.content().string("вы зарегестрировались на рейс!"));
    }
    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_registerFlights_Exception() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/client/registerFlights").param("id","1"));
    }
    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_review_OK() throws Exception{
        ReviewsDto reviewsDto = new ReviewsDto();
        reviewsDto.setMessage("HELLO");
        reviewsDto.setIdFlight(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/client/review").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reviewsDto)))
                .andExpect(MockMvcResultMatchers.content().string("Отзыв отравлен!"));

    }
    @Test
    @WithMockUser(username = "ST", roles = "CLIENT")
    public void test_review_Exception() throws Exception{
        ReviewsDto reviewsDto = new ReviewsDto();
        reviewsDto.setMessage("HELLO");
        reviewsDto.setIdFlight(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/client/review").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reviewsDto)));
    }
}