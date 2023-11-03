package com.example.aiport.controller.v1;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.PlanesDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.entity.RequestsTakingPlaneEntity;
import com.example.aiport.exception.FailSendException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidFlightsException;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('DISPATCHER')")
@RequestMapping(value = "/dispatcher")
public class DispatcherController {
    private final DispatcherService dispatcherService;
    @Autowired
    public DispatcherController(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @PostMapping(value = "/createPlane")
    public String createAirPlans(@RequestBody PlanesDto planesDto) throws InvalidPlaneException {
            dispatcherService.createPlane(planesDto.getModel(), planesDto.getMarka(), planesDto.getCountPlace());
            return "Запрос отправлен Главному инженеру и в скорем времени он возьмется за дело!";
    }
    @GetMapping(value = "/getPlanes")
    public List<PlaneStatusDto> getPlanes(@RequestParam(name = "filter",required = false)String filter){
        return dispatcherService.getPlanes(filter);
    }

    @PostMapping(value = "/send")
    public String send(@RequestParam(name = "id")Long id) throws FailSendException {
            dispatcherService.sendChiefDispatcher(id);
            return "Вы отправили самолет на потверждение Главному диспетчеру!";
    }
    @PostMapping(value = "/createFlights")
    public String createFlights(@RequestBody FlightsDto flightsDto) throws InvalidFlightsException {
            dispatcherService.createFlights(flightsDto.getTitle(),flightsDto.getPlaceOfDeparture(),flightsDto.getId());
            return "Рейс создан!";
    }
    @GetMapping(value = "/getFlights")
    public List<FlightsDto> getFlights(){
        return dispatcherService.getConfirmFlights();
    }

    @PostMapping(value = "/takeOffFly")
    public String sendFlights(@RequestParam(name = "id")Long id) throws FailSendException {
            dispatcherService.sendChiefDispatcherFlights(id);
            return "Вы отправили рейс на потверждение отправки Главного диспетчера!";
    }
    @GetMapping(value = "/takingPlane")
    public List<RequestTakingPlaneDto> getTaking(){
        return dispatcherService.getTakingPlane();
    }



    @PostMapping(value = "/adoption")
    public String adoption(@RequestParam(name = "id")Long id) throws FlyException {
            dispatcherService.acceptTakingPlane(id);
            return "Вы приняли запрос и отдали главному диспетчеру!";
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Диспетчер - роль которая может создавать самолеты и рейсы а также принимать решение \n " +
                "getPlanes - посмотреть самолеты \n " +
                "send- отправить главному диспечтеру \n " +
                "createPlane - создает  самолет \n " +
                "createFlight - создает рейс \n " +
                "takeOffFly - отправить самолети на вылет \n " +
                "getFlight - вывести самолеты \n " +
                "takingPlane - получить список на посадку самолетов \n" +
                "adoption - посадить самолет";
    }

}
