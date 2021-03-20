package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.repository.UserRepository;
import com.lyon1.iot_weather_station.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByIdUser(Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public User findByEmailUser(String email) throws EntityNotFoundException {
        return userRepository.findFirstByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

    @Override
    public User findByEmailAndPasswordUser(String email, String password) throws EntityNotFoundException, CustomException {
        User userFound = findByEmailUser(email);

        if (!passwordEncoder.matches(password, userFound.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return userFound;
    }

    @Override
    public User saveUser(User user) throws CustomException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User userSaved;
        try {
            userSaved = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(HttpStatus.CONFLICT, "User with email "
                    + user.getEmail() + " already exists", e);
        }

        return userSaved;
    }

    @Override
    public List<User> findAll() throws EntityNotFoundException {
        return userRepository.findAll();
    }
}