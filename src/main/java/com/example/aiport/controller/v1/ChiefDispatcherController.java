package com.example.aiport.controller.v1;

import com.example.aiport.dto.*;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.FailConfirmException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.service.ChiefDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('CHIEF_DISPATCHER')")
@RequestMapping(value = "/chiefDispatchers")
public class ChiefDispatcherController {
    private final ChiefDispatcherService chiefDispatcherService;

    @Autowired
    public ChiefDispatcherController(ChiefDispatcherService chiefDispatcherService) {
        this.chiefDispatcherService = chiefDispatcherService;
    }

    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlanes(@RequestParam(name = "filter",required = false)String filter) {
        return chiefDispatcherService.getPlanes(filter);
    }

    @PostMapping(value = "/confirm")
    public String confirm(@RequestBody ConfirmDto confirmDto) throws FailConfirmException {

            PromoltionEnum promoltionEnum = chiefDispatcherService.confirm(confirmDto.getId(),confirmDto.getAnswer());
            if (PromoltionEnum.ACCEPT.equals(promoltionEnum)){
                return "самолет зарегестрирован!";
            }
            if (PromoltionEnum.REFUSE.equals(promoltionEnum)){
                return "Вы отказались от этого самолета!";
            }
        return null;
    }


    @PostMapping(value = "/confirmFlights")
    public String confirmFlights(@RequestBody ConfirmDto confirmDto) throws FailConfirmException {
            PromoltionEnum promoltionEnum = chiefDispatcherService.confirmFlights(confirmDto.getId(),confirmDto.getAnswer());
            if (PromoltionEnum.ACCEPT.equals(promoltionEnum)){
                return "рейс зарегестрирован!";
            }
            if (PromoltionEnum.REFUSE.equals(promoltionEnum)){
                return "Вы отказались от этого рейса!";
            }
        return null;
    }
    @GetMapping(value = "/getFlights")
    public List<FlightsDto> getConfirmFlights(@RequestParam(name = "filter",required = false)String filter){
        return chiefDispatcherService.getConfirmFlights(filter);
    }


    @PostMapping(value = "/confirmTakeFly")
    public String confirmSendingFlights(@RequestBody ConfirmDto confirmDto) throws FailConfirmException {
            PromoltionEnum promoltionEnum = chiefDispatcherService.confirmSendingFlights(confirmDto.getId(),confirmDto.getAnswer());
            if (PromoltionEnum.ACCEPT.equals(promoltionEnum)){
                return "ответ передан пилоту и самолет скоро взлетит!";
            }
            if (PromoltionEnum.REFUSE.equals(promoltionEnum)){
                return "Ваш ответ был отрицательными, рейс отменен!";
            }
        return null;
    }


    @GetMapping(value = "/getAdoption")
    public List<RequestTakingPlaneDto> getAdoptionFlights(){
        return chiefDispatcherService.getAdoptionFlights();
    }
    @PostMapping(value = "/confirmAdoption")
    public String confirmAdoption(@RequestParam(name = "id")Long id) throws FlyException {
            chiefDispatcherService.confirmAdoption(id);
            return "Вы потвердили посадку самолета";
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Главный диспетчер - роль которая потверждает абсолютно все действие Диспетчера! \n" +
                "getPlanes - выводит самолеты \n" +
                "confirm - потверждает самолет \n " +
                "confirmFlights - потвержает рейс \n" +
                "confirmTakeFly - потверждает вылет самолета \n " +
                "confirmAdoption - потверждает посадку самолета \n " +
                "getFlights - выводит рейсы \n " +
                "getAdoption - выводит запросы на посадку";
    }
}
