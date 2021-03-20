package com.lyon1.iot_weather_station.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@JsonSerialize(as = Device.class)
@Table(name = "device")
public class Device {

    @Id
    @Column(nullable = false, name = "id", length = 36)
    private String id;

    @Basic
    @Column(name = "min_temp")
    private Double minTemperature;

    @Basic
    @Column(name = "max_temp")
    private Double maxTemperature;

    @Basic
    @Column(name = "turn_off_relay", columnDefinition = "bit(1) DEFAULT 0")
    private Boolean turnOffRelay = Boolean.FALSE;

    @Basic
    @Column(name = "m_interval", columnDefinition = "smallint DEFAULT 1")
    private Integer minuteInterval = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Boolean getTurnOffRelay() {
        return turnOffRelay;
    }

    public void setTurnOffRelay(Boolean turnOffRelay) {
        this.turnOffRelay = turnOffRelay;
    }

    public Integer getMinuteInterval() {
        return minuteInterval;
    }

    public void setMinuteInterval(Integer minuteInterval) {
        this.minuteInterval = minuteInterval;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}