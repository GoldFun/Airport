package com.example.aiport.controller.v1;

import com.example.aiport.dto.ChangeRoleDto;
import com.example.aiport.dto.ReadingDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize(value = "hasAnyRole('SYSTEM_ADMIN')")
@RequestMapping(value = "/admin")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;
    @Autowired
    public SystemAdminController(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }



    @PostMapping(value = "/changeRole") //меняет роль пользователю
    public String changeRole(@RequestBody ChangeRoleDto changeRoleDto) throws InvalidLoginException, InvalidRoleException {
            systemAdminService.changeRole(changeRoleDto.getRole(),changeRoleDto.getLogin());
            return "Вы успешно изменили роль пользователю!";
    }
    @GetMapping(value = "/getUserList") //выведет пользователей
    public List<UserDto> getUserList(){
        return systemAdminService.getUserList();
    }

    @GetMapping(value = "/getReadingList")
    private List<ReadingDto> getReadings(){
        System.out.println(1);
        return systemAdminService.getReadings();
    }

    @GetMapping(value = "/help")
    public String help(){
        return "Админ - роль которая может менять роль пользователя и смотреть всех пользователей \n" +
                "changeRole - меняет роль пользователю \n" +
                "getUserList - смотрит список всех пользователей";
    }
}

