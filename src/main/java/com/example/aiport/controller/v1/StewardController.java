package com.example.aiport.controller.v1;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('STEWARD')")
@RequestMapping(value = "/steward")
public class StewardController {
    private final StewardService stewardService;
    @Autowired
    public StewardController(StewardService stewardService) {
        this.stewardService = stewardService;
    }

    @PostMapping(value = "/briefing")
    public String briefing() throws StewardException {
            stewardService.Briefing();
            return "инстуктаж проведен!";
    }
    @PostMapping(value = "/giveFood")
    public String giveFood() throws StewardException {
            stewardService.giveFood();
            return "раздача еды проведена!";
    }
    @PostMapping(value = "/wantPlane")
    public String wantPlane(@RequestParam(name = "id")Long id) throws InvalidPlaneException {
        stewardService.wantPlane(id);
        return "Вы получили в распоряжении самолет!";
    }
    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlanes(){
        return stewardService.getPlanes();
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Стюард - раздает еду и проводит инструктаж \n" +
                "giveFood - раздает еду \n" +
                "briefing - проводит инструктаж";
    }
}
