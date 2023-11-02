package com.example.aiport.controller.v1;


import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;
import com.example.aiport.service.ChiefEngineerService;
import com.example.aiport.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('ENGINEER')")
@RequestMapping(value = "/engineer")
public class EngineerController {
    private final EngineerService engineerService;

    @Autowired
    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlaneList(@RequestParam(name = "filter",required = false)String filter){
        return engineerService.getPlanes(filter);
    }

    @PostMapping(value = "/techReview")
    public String techReview(@RequestParam(name = "id")Long id) throws FailTechReviewException {
            engineerService.techReview(id);
            return "Вы успешно сделали Технический осмотр!";
    }
    @PostMapping(value = "/repair")
    public String repair(@RequestParam(name = "id")Long id) throws FailRepairException {
            engineerService.repair(id);
            return "Самолет успешно отремонтирован!";
    }
    @GetMapping(value = "/getNewPlanes")
    public List<PlaneStatusDto> getNewPlanes(){
        return engineerService.getNewPlanes();
    }

    @PostMapping(value = "/refuel")
    public String refuel(@RequestParam(name = "id")Long id) throws FailRefuelException {
            engineerService.refuel(id);
            return "Инженер залили в бензин самолет";
    }
    @GetMapping(value = "/help")
    public String help(){
        return "Инженер - роль которая чинит самолеты \n" +
                "techReview - проверят самолет не сломан ли он \n" +
                "repair - чинит самолет \n" +
                "refuel -  заправляет самолет \n " +
                "getPlanes - посмотреть на самолеты \n" +
                "getNewPlanes - посмотреть на новые самолеты";
    }






}
