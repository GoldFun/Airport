package com.example.aiport.service.impl;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.ReviewsDtoWithUsers;
import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.repository.*;
import com.example.aiport.service.PilotService;
import com.example.aiport.validators.PilotValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PilotServiceImpl implements PilotService {
    private final UsersEntityRepository usersEntityRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final PilotValidator pilotValidator;
    private final RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository;
    private final ReviewsEntityRepository reviewsEntityRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public PilotServiceImpl(UsersEntityRepository usersEntityRepository,
                            AirPlansEntityRepository airPlansEntityRepository,
                            PilotValidator pilotValidator,
                            RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository,
                            FlightsEntityRepository flightsEntityRepository,
                            HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository,
                            ReviewsEntityRepository reviewsEntityRepository, UserRolesRepository userRolesRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.pilotValidator = pilotValidator;
        this.requestsTakingPlaneEntityRepository = requestsTakingPlaneEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.historyPastFlightsEntityRepository = historyPastFlightsEntityRepository;
        this.reviewsEntityRepository = reviewsEntityRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public String getAnswer() {
        System.out.println(123);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(authenticationUser.get().getAirPlansEntity());


            if (flights.get().getStatus().equals("CONFIRMED_FLIGHTS")){
                return "Мы готовы к запуску";
            }else {
                return "Мы не получили ответ главного диспетчера!";
            }
        }
        return null;
    }

    @Override
    public void takeOff() throws FlyException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            pilotValidator.validateTakeOff(authenticationUser.get());
            authenticationUser.get().getAirPlansEntity().setStatus("FLY");
            airPlansEntityRepository.save(authenticationUser.get().getAirPlansEntity());



        }
        TimeUnit.SECONDS.sleep(3);
    }

    @Override
    public void planeLanding() throws FlyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            pilotValidator.validatePlaneLanding(authenticationUser.get().getAirPlansEntity().getId());

            Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(authenticationUser.get().getAirPlansEntity());
            System.out.println(1);

            RequestsTakingPlaneEntity requestsTakingPlaneEntity = new RequestsTakingPlaneEntity();

            requestsTakingPlaneEntity.setAirportEntity(authenticationUser.get().getAirportEntity());
            requestsTakingPlaneEntity.setPlaceOfDeparture(flights.get().getPlaceOfDeparture());
            requestsTakingPlaneEntity.setModelPlane(authenticationUser.get().getAirPlansEntity().getModel());
            requestsTakingPlaneEntity.setFlights(flights.get());
            requestsTakingPlaneEntity.setId(authenticationUser.get().getAirPlansEntity().getId());
            requestsTakingPlaneEntityRepository.save(requestsTakingPlaneEntity);


        }
    }

    @Override
    public void landing() throws FlyException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            pilotValidator.validateLanding(authenticationUser.get());

            Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(authenticationUser.get().getAirPlansEntity());
            List<UsersEntity> FlightsList = usersEntityRepository.findUsersByFlights(flights.get().getId());

            Optional<FlightsEntity> flight = flightsEntityRepository.findByAirPlansEntity(authenticationUser.get().getAirPlansEntity());
            flight.get().setDate(LocalDate.now());
            flightsEntityRepository.save(flight.get());
            for (UsersEntity user : FlightsList){
                System.out.println(1);
                HistoryPastFlightsEntity historyPastFlightsEntity = new HistoryPastFlightsEntity();
                historyPastFlightsEntity.setUsersEntity(user);
                historyPastFlightsEntity.setFlights(user.getFlightsEntity());
                historyPastFlightsEntity.setDate(flights.get().getDate());
                historyPastFlightsEntityRepository.save(historyPastFlightsEntity);
                user.setFlightsEntity(null);
                usersEntityRepository.save(user);
            }

            authenticationUser.get().getAirPlansEntity().setStatus("CONFIRM");
            flights.get().setStatus("CONFIRM");
            airPlansEntityRepository.save(authenticationUser.get().getAirPlansEntity());
            flightsEntityRepository.save(flights.get());

        }
    }

    @Override
    public List<ReviewsDtoWithUsers> getReviews() {
        List<ReviewsDtoWithUsers> list = new ArrayList<>();
        for (ReviewsEntity reviews : reviewsEntityRepository.findAll()){
            ReviewsDtoWithUsers reviewsDtoWithUsers = new ReviewsDtoWithUsers();
            reviewsDtoWithUsers.setMessage(reviews.getMessage());
            reviewsDtoWithUsers.setLogin(reviews.getUser().getLogin());
            reviewsDtoWithUsers.setIdFlight(reviews.getFlights().getAirPlansEntity().getId());
            list.add(reviewsDtoWithUsers);
        }
        return list;
    }

    @Override
    public void wantPlane(Long id) throws InvalidPlaneException {
        AirPlansEntity plane = pilotValidator.validateWantPlaneNull(id);
        pilotValidator.validateWantPlane(id, plane);
        Optional<AirPlansEntity> airPlansEntity = airPlansEntityRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            authenticationUser.get().setAirPlansEntity(airPlansEntity.get());
            usersEntityRepository.save(authenticationUser.get());
        }
    }

    @Override
    public List<PlaneStatusDto> getPlanes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<PlaneStatusDto> list = new ArrayList<>();
            for (AirPlansEntity plans : airPlansEntityRepository.findAll()) {
                if (plans.getStatus().equals("CONFIRM")) {
                    Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(authenticationUser.get());
                    List<UsersEntity> user = usersEntityRepository.findByRolesIdAndAirplanes(userRoles.get().getRolesEntity().getId(),plans.getId());
                    if (user.isEmpty()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        planeStatusDto.setPlaneStatus(PlaneStatus.valueOf(plans.getStatus()));
                        planeStatusDto.setModel(plans.getModel());
                        planeStatusDto.setMarka(plans.getMarka());
                        planeStatusDto.setId(plans.getId());
                        planeStatusDto.setCountPlace(plans.getCountPlace());
                        list.add(planeStatusDto);
                    }
                }
            }
            return list;
        }
        return null;
    }
}
