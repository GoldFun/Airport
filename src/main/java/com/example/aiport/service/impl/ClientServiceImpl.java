package com.example.aiport.service.impl;

import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.HistoryPastFlightsDto;
import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.exception.*;
import com.example.aiport.repository.*;
import com.example.aiport.service.ClientService;
import com.example.aiport.validators.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final UsersEntityRepository usersEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final ClientValidator clientValidator;
    private final RolesEntityRepository rolesEntityRepository;
    private final RequestsJobsEntityRepository requestsJobsEntityRepository;
    private final ReviewsEntityRepository reviewsEntityRepository;
    private final HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository;


    @Autowired
    public ClientServiceImpl(UsersEntityRepository usersEntityRepository,
                             FlightsEntityRepository flightsEntityRepository,
                             ClientValidator clientValidator,
                             RolesEntityRepository rolesEntityRepository,
                             RequestsJobsEntityRepository requestsJobsEntityRepository,
                             ReviewsEntityRepository reviewsEntityRepository,
                             HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.clientValidator = clientValidator;
        this.rolesEntityRepository = rolesEntityRepository;
        this.requestsJobsEntityRepository = requestsJobsEntityRepository;
        this.reviewsEntityRepository = reviewsEntityRepository;
        this.historyPastFlightsEntityRepository = historyPastFlightsEntityRepository;
    }

    @Override
    public void applyJob(String role, String message) throws InvalidRoleException, EmptyMessageException, InvalidLoginException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            clientValidator.validateApplyJobByRole(role);
            clientValidator.validateApplyJobByMessage(message);
            clientValidator.validateApplyJobsByLogin(authenticationUser.get().getLogin());

            RequestsJobsEntity requestsJobs = new RequestsJobsEntity();
            requestsJobs.setMessage(message);

            for (RolesEntity roles : rolesEntityRepository.findAll()) {
                if (roles.getTitle().equals(role)) {
                    requestsJobs.setRolesEntity(roles);
                }
            }

            requestsJobs.setLogin(authenticationUser.get().getLogin());
            requestsJobsEntityRepository.save(requestsJobs);
        }
    }

    @Override
    public List<FlightsDto> getFlights() {
        List<FlightsDto> flightsDtoList = new ArrayList<>();
        for (FlightsEntity flights : flightsEntityRepository.findAll()) {
            FlightsDto flightsDto = new FlightsDto();
            FlightsStatusEnum flightsStatusEnum = FlightsStatusEnum.valueOf(flights.getStatus());
            if (FlightsStatusEnum.CONFIRM.equals(flightsStatusEnum)) {
                flightsDto.setId(flights.getId());
                flightsDto.setTitle(flights.getTitle());
                flightsDto.setPlaceOfDeparture(flights.getPlaceOfDeparture().getId());
                flightsDtoList.add(flightsDto);
            }
        }
        return flightsDtoList;
    }

    @Override
    public void registerFlights(Long id) throws InvalidFlightsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            clientValidator.validateRegisterFlights(id);


            Optional<FlightsEntity> flights = flightsEntityRepository.findById(id);
            if (!flights.get().getStatus().equals("CONFIRM")){
                throw new InvalidFlightsException("Рейс недоступен!");
            }
            authenticationUser.get().setFlightsEntity(flights.get());
            flightsEntityRepository.save(flights.get());



            List<UsersEntity> userList = usersEntityRepository.findUsersByFlights(id);
            if(flights.get().getAirPlansEntity().getCountPlace().equals(userList.size())){
                flights.get().setStatus("IS_BUSY");
                flights.get().getAirPlansEntity().setStatus("IS_BUSY");
                flightsEntityRepository.save(flights.get());
            }

        }
    }

    @Override
    public void logOutFlights() throws LogOutException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            clientValidator.validateLogoutFlights(authenticationUser.get().getId());
            authenticationUser.get().setFlightsEntity(null);
            usersEntityRepository.save(authenticationUser.get());
        }
    }

    @Override
    public FlightsDto getFlight() throws InvalidFlightsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            if (Objects.isNull(authenticationUser.get().getFlightsEntity())){
                throw new InvalidFlightsException("Вы еще не зарегестрировались на рейс!");
            }
            FlightsDto flightsDto = new FlightsDto();
            flightsDto.setId(authenticationUser.get().getFlightsEntity().getId());
            flightsDto.setTitle(authenticationUser.get().getFlightsEntity().getTitle());
            flightsDto.setPlaceOfDeparture(authenticationUser.get().getFlightsEntity().getPlaceOfDeparture().getId());
            return flightsDto;
        }
        return null;
    }

    @Override
    public void review(String message, Long id) throws ReviewsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<FlightsEntity> flights = clientValidator.validateReviews(id, message);

            ReviewsEntity review = new ReviewsEntity();
            review.setUser(authenticationUser.get());
            review.setFlights(flights.get());
            review.setMessage(message);
            reviewsEntityRepository.save(review);
        }
    }

    @Override
    public List<HistoryPastFlightsDto> getHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            List<HistoryPastFlightsDto> list = new ArrayList<>();

            for (HistoryPastFlightsEntity history : historyPastFlightsEntityRepository.findAll()){
                if (history.getUsersEntity().getLogin().equals(authenticationUser.get().getLogin())){
                HistoryPastFlightsDto historyPastFlightsDto = new HistoryPastFlightsDto();
                historyPastFlightsDto.setAirport(history.getFlights().getAirportEntity().getTitle());
                historyPastFlightsDto.setPlaceOfDeparture(history.getFlights().getPlaceOfDeparture().getTitle());
                historyPastFlightsDto.setTitle(history.getFlights().getTitle());
                historyPastFlightsDto.setDate(String.valueOf(history.getFlights().getDate()));
                list.add(historyPastFlightsDto);
            }
            }
            return list;
        }
        return null;
    }


}
