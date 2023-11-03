package com.example.aiport.controller.v1;

import com.example.aiport.dto.ChangeRoleDto;
import com.example.aiport.dto.RecruitDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.RequestsJobsEntity;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.ActionOneselfException;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.service.AirportManagerService;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
//@PreAuthorize(value = "hasAnyRole('MANAGER_AIRPORT')")
@RequestMapping(value = "/manager")
public class AirportManagerController {
    private final AirportManagerService airportManagerService;

    @Autowired
    public AirportManagerController(AirportManagerService airportManagerService) {
        this.airportManagerService = airportManagerService;
    }

    @PostMapping(value = "/changeRole")
    public String changeRole(@RequestBody ChangeRoleDto changeRoleDto) throws InvalidLoginException, InvalidRoleException {
        System.out.println("131221`341234");

            airportManagerService.changeRole(changeRoleDto.getLogin(),changeRoleDto.getRole());
            return "Вы успешно изменили роль пользователю!";

    }
    @DeleteMapping(value = "/retire")
    public String retire(@RequestParam(name = "username")String login) throws InvalidLoginException, InvalidRoleException, ActionOneselfException {
            System.out.println(login);
            airportManagerService.retire(login);
            return "Вы успешно уволили своего работника!";

    }
    @GetMapping(value = "/getRequestsJobs")
    public List<RequestsJobsEntity> getRequestsJobs(){
        return airportManagerService.getRequestJobsList();
    }

    @PostMapping(value = "/recruit")
    public String recruit(@RequestBody RecruitDto recruitDto) throws InvalidLoginException, EmptyAnswerException {

            PromoltionEnum promoltionEnum = airportManagerService.recruit(recruitDto.getLogin(),recruitDto.getAnswer());
            if (PromoltionEnum.ACCEPT.equals(promoltionEnum)){
                return "вы наняли нового работника!";
            }else if (PromoltionEnum.REFUSE.equals(promoltionEnum)){
                return "вы отказали нанимать этого работника!";
            }
        return null;
    }
    @GetMapping(value = "/getUserList")
    public List<UserDto> getUserList(){
        return airportManagerService.getUserList();
    }

    @PostMapping(value = "/reading")
    public String reading(){
    airportManagerService.reading();
    return "Отсчёт отправлен!";
    }

    @GetMapping (value = "/help")
    public String help(){
        return "Менеджер Аэрпорта - роль которой доступны такие функции как \n" +
                "Изменять роль, нанимать людей и увольнять их \n" +
                "(changeRole)    (recruit)        (retire) \n" +
                "Он также может смотреть на список пользователей (getUserList) и ихние заявки на работу (getRequestsJobs)";
    }
}
