package com.lyon1.iot_weather_station.service;

import com.lyon1.iot_weather_station.exception.CustomException;
import com.lyon1.iot_weather_station.repository.DeviceRepository;
import com.lyon1.iot_weather_station.model.Device;
import com.lyon1.iot_weather_station.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserService userService;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, UserService userService) {
        this.deviceRepository = deviceRepository;
        this.userService = userService;
    }

    @Override
    public Device findByIdDevice(String id) throws EntityNotFoundException {
        return deviceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Device with id " + id + " not found")
        );
    }

    @Override
    public Set<Device> findByUserIdDevice(Long userId) throws EntityNotFoundException {
        User user = userService.findByIdUser(userId);
        return user.getDevices();
    }

    @Override
    public boolean existsByIdDevice(String id) {
        return deviceRepository.existsById(id);
    }

    @Override
    public boolean isPairedByIdDevice(String id) throws EntityNotFoundException {
        Device device = findByIdDevice(id);
        return device.getUser() != null;
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device updateTurnOffRelayByIdDevice(String id, boolean turnOffRelay) throws EntityNotFoundException {
        Device device = findByIdDevice(id);
        device.setTurnOffRelay(turnOffRelay);

        return deviceRepository.save(device);
    }

    @Override
    public Device updateUserByIdDevice(String id, Long userId) throws EntityNotFoundException, CustomException {
        Device device = findByIdDevice(id);

        if (device.getUser() != null) {
            throw new CustomException(HttpStatus.CONFLICT, "Device with id " + id + " is already paired");
        }

        User user = userService.findByIdUser(userId);
        device.setUser(user);

        return deviceRepository.save(device);
    }

    @Override
    public Device releaseByIdDevice(String id) throws EntityNotFoundException {
        Device device = findByIdDevice(id);

        Device defaultDevice = new Device();
        device.setMinTemperature(defaultDevice.getMinTemperature());
        device.setMaxTemperature(defaultDevice.getMaxTemperature());
        device.setTurnOffRelay(defaultDevice.getTurnOffRelay());
        device.setMinuteInterval(defaultDevice.getMinuteInterval());
        device.setUser(null);

        return deviceRepository.save(device);
    }

    @Override
    public Device updateByIdDevice(String id, Device deviceDetails) throws EntityNotFoundException {
        Device device = findByIdDevice(id);

        device.setMaxTemperature(deviceDetails.getMaxTemperature());
        device.setMinTemperature(deviceDetails.getMinTemperature());
        device.setMinuteInterval(deviceDetails.getMinuteInterval());
        device.setTurnOffRelay(deviceDetails.getTurnOffRelay());

        return deviceRepository.save(device);
    }
}