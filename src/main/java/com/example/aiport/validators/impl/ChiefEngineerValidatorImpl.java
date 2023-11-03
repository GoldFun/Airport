package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.InvalidRequestException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.ChiefEngineerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ChiefEngineerValidatorImpl implements ChiefEngineerValidator {
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    @Autowired
    public ChiefEngineerValidatorImpl(AirPlansEntityRepository airPlansEntityRepository,
                                      UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public void validateGiveOrder(Long id, String statusPlane) throws InvalidRequestException {
        if (Objects.isNull(id)){
            throw new InvalidRequestException("Пустой id!");
        }
        if (Objects.isNull(statusPlane)||statusPlane.isEmpty()){
            throw new InvalidRequestException("Нужно дать статус самолету чтобы инженеры поняли что с ним надо делать!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);

            if(airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())){

                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                if (PlaneStatus.TECH_REVIEW.name().equalsIgnoreCase(plane.get().getStatus())||
                        PlaneStatus.REPAIR.name().equalsIgnoreCase(plane.get().getStatus())||
                        PlaneStatus.REFUEL.name().equalsIgnoreCase(plane.get().getStatus())){
                    throw new InvalidRequestException("Эти самолеты уже переданы инженерам!");
                }



                try {

                    if (plane.get().getStatus().equals("NORMAL")||plane.get().getStatus().equals("BROKEN")){
                        if (statusPlane.equalsIgnoreCase("REPAIR")){


                            if (PlaneStatus.NORMAL.name().equals(plane.get().getStatus())){
                                throw new InvalidRequestException("Этот самолет уже исправен!");
                            }

                        }else {
                            throw new InvalidRequestException("Этот самолет уже прошел технический осмотр но не может быть залит!");
                        }

                    }
                }catch (NullPointerException e) {
                    if (statusPlane.equalsIgnoreCase("repair")) {
                        throw new InvalidRequestException("Сначало отправьте этот самолет на технический осмотр!");
                    }
                }

                if (statusPlane.equalsIgnoreCase("refuel")){
                    if (plane.get().getStatus().equals("IS_BUSY")){
                        System.out.println("1232");
                        return;
                    }
                    throw new InvalidRequestException("Самолет не соответствует статусу REFUEL!");
                }




                for (AirPlansEntity planes : airPlansEntityRepository.findAll()){
                    if (planes.getId().equals(id)){

                        if (PlaneStatus.TECH_REVIEW.name().equalsIgnoreCase(statusPlane)||PlaneStatus.REPAIR.name().equalsIgnoreCase(statusPlane)||PlaneStatus.REFUEL.name().equalsIgnoreCase(statusPlane)){
                            return;
                        }
                        throw new InvalidRequestException("такого статуса для самолет несуществует!");
                    }
                }
                throw new InvalidRequestException("Самолета с таким id несуществует!");
            }
            throw new InvalidRequestException("Этот самолет принадлежит другому Аэрпорту!");
        }



    }

    @Override
    public void validateSend(Long id) throws InvalidRequestException {
        if (Objects.isNull(id)){
            throw new InvalidRequestException("Пустой id");
        }

        Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
        if (PlaneStatus.REFILLED.name().equals(plane.get().getStatus())){
            return;
        }
        throw new InvalidRequestException("Самолет не соответствует статусу REFILLED!");
    }
}
