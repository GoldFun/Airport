package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.service.ChiefStewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('CHIEF_STEWARD')")
@RequestMapping(value = "/chiefSteward")
public class ChiefStewardController {
    private final ChiefStewardService chiefStewardService;
    @Autowired
    public ChiefStewardController(ChiefStewardService chiefStewardService) {
        this.chiefStewardService = chiefStewardService;
    }

    @PostMapping(value = "/scheduleBriefing")
    public String scheduleBriefing() throws StewardException {
        chiefStewardService.scheduleBriefing();
        return "вы назначили стюардам инструктаж!";
    }
    @PostMapping(value = "/scheduleGiveFood")
    public String scheduleGiveFood() throws StewardException {
            chiefStewardService.scheduleGiveFood();
            return "вы назначили стюардам раздачу еды!";
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Главный стюард - роль которая радает приказы стюардам чтобы они делали инструктаж и раздавали еду" +
                "scheduleBriefing - дает приказ стюардам для проведение инструктажа \n" +
                "scheduleGiveFood - дает приказ о раздаче еды";
    }
    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlanes(){
        return chiefStewardService.getPlanes();
    }
    @PostMapping(value = "/wantPlane")
    public String wantPlane(@RequestParam(name = "id")Long id) throws InvalidPlaneException {
        chiefStewardService.wantPlane(id);
        return "Вы получили в распоряжении самолет!";
    }

}
