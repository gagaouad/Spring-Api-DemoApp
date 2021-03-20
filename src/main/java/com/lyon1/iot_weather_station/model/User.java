package com.lyon1.iot_weather_station.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonSerialize(as = User.class)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Basic
    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Basic
    @Column(nullable = false, name = "password")
    private String password;

    @Basic
    @Column(nullable = false, name = "first_name", length = 30)
    private String firstName;

    @Basic
    @Column(nullable = false, name = "last_name", length = 30)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    @JsonIgnore
    private Set<Device> devices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public Set<Device> getDevices() {
        return devices;
    }

    @JsonProperty
    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
}