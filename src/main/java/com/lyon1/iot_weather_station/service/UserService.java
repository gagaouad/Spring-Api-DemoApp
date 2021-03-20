package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface UserService {

    User findByIdUser(Long id) throws EntityNotFoundException;

    User findByEmailUser(String email) throws EntityNotFoundException;

    User findByEmailAndPasswordUser(String email, String password) throws EntityNotFoundException;

    User saveUser(User user) throws EntityNotFoundException, CustomException;

    List<User> findAll() throws  EntityNotFoundException;
}