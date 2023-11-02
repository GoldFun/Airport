package com.example.aiport.controller.v1;

import com.example.aiport.dto.GiveOrderDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidRequestException;
import com.example.aiport.service.ChiefEngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@PreAuthorize(value = "hasAnyRole('CHIEF_ENGINEER')")
@RequestMapping(value = "/chiefEngineer")
public class ChiefEngineerController {
   private final ChiefEngineerService chiefEngineerService;

   @Autowired
   public ChiefEngineerController(ChiefEngineerService chiefEngineerService) {
       this.chiefEngineerService = chiefEngineerService;
   }


   @PostMapping(value = "/giveOrder")
    public String giveOrder(@RequestBody GiveOrderDto giveOrderDto) throws InvalidRequestException {
           chiefEngineerService.giveOrder(giveOrderDto.getId(),giveOrderDto.getPlaneStatus());
           return "Вы отдали приказ Инженерам и в скорем времени они займутся за дело!";
   }
    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlaneList(@RequestParam(name = "filter",required = false)String filter){
        return chiefEngineerService.getPlaneList(filter);
    }

    @GetMapping(value = "/help")
    public String help(){
       return "Главный инженер -  роль которая раздает приказы инженерам, а именно починить, проверить или запрвить самолет \n " +
               "getPlanes - выводит самолеты \n" +
               "giveOrder - раздает приказы (проверить, починить, запрваить)";
    }
}
