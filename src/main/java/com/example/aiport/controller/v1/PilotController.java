package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.PlanesDto;
import com.example.aiport.dto.ReviewsDtoWithUsers;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('PILOT')")
@RequestMapping(value = "/pilot")
public class PilotController {
    private final PilotService pilotService;
    @Autowired
    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping(value = "/answer")
    public String get(){
        return pilotService.getAnswer();
    }
    @PostMapping(value = "/takeOff")
    public String takeOff() throws FlyException, InterruptedException {
            pilotService.takeOff();
        return "Самолет взлетел";
    }
    @PostMapping(value = "/planeLanding")
    public String planeLanding() throws FlyException {
            pilotService.planeLanding();
            return "Запрос диспечтером из другого Аэротра отправлен, ждем разрешение на посадку!";
    }
    @PostMapping(value = "/landing")
    public String landing() throws FlyException {
            pilotService.landing();
            return "Рейс прошел успешно и вы смогли долететь до другого Аэрпотра!";
    }
    @GetMapping(value = "/getReviews")
    public List<ReviewsDtoWithUsers> getReviews(){
        return pilotService.getReviews();
    }

    @PostMapping(value = "/wantPlane")
    public String wantPlane(@RequestParam(name = "id")Long id) throws InvalidPlaneException {
        pilotService.wantPlane(id);
        return "Вы получили в распоряжении самолет!";
    }
    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlanes(){
        return pilotService.getPlanes();
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Пилот - роль которая управляет самолетом \n " +
                "answer - получить ответ главного диспетчера на вылет \n" +
                "takeOff - взлетает самолет \n" +
                "planeLanding - отправляет запрос на посадку самолета \n" +
                "landing - посадка самолета";
    }
}
