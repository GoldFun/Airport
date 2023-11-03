package com.example.aiport.controller.v1;

import com.example.aiport.dto.ApplyJobDto;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.HistoryPastFlightsDto;
import com.example.aiport.dto.ReviewsDto;
import com.example.aiport.exception.*;
import com.example.aiport.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('CLIENT')")
@RequestMapping(value = "/client")
public class ClientController {
    private  final ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping(value = "/applyJob")
    public String applyJob(@RequestBody ApplyJobDto applyJobDto) throws InvalidLoginException, InvalidRoleException, EmptyMessageException {
            clientService.applyJob(applyJobDto.getRole(),applyJobDto.getMessage());
            return "Ваш запрос отправлен Менеджеру и в скором времени рассмотрит ваше предложение на работу!";
    }
    @GetMapping(value = "/getFlights")
    public List<FlightsDto> getFlights(){
        return clientService.getFlights();
    }

    @PostMapping(value = "/registerFlights")
    public String registerFlights(@RequestParam(name = "id")Long id) throws InvalidFlightsException {
            clientService.registerFlights(id);
            return "вы зарегестрировались на рейс!";
    }
    @GetMapping(value = "/logOut")
    public String logOut() throws LogOutException {
            clientService.logOutFlights();
            return "Вы успешно вышли из рейса!";
    }
    @GetMapping(value = "/getYourFlight")
    public FlightsDto getFlight() throws InvalidFlightsException {
            return clientService.getFlight();
    }

    @PostMapping(value = "/review")
    public String review(@RequestBody ReviewsDto reviewsDto) throws ReviewsException {
            clientService.review(reviewsDto.getMessage(),reviewsDto.getIdFlight());
            return "Отзыв отравлен!";

            

    }
    @GetMapping(value = "/getHistory")
    public List<HistoryPastFlightsDto> getHistory(){
        return clientService.getHistory();
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Клиент - вы клиент который может наняться на нашу работу, зарегестрироваться на рейс, и оставить отзыв \n" +
                "applyJob - может подать заявку на работу \n " +
                "getFlight - посмотреть на рейсы \n " +
                "registerFlights - регистрирует тебя на рейс \n" +
                "logOut - выйти из рейса \n " +
                "getYourFlight - посмотреть на свой рейс \n" +
                "review - оставить отзыв";
    }
}
