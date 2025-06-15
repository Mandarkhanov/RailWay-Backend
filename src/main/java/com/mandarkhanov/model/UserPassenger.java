package com.mandarkhanov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "user_passengers")
public class UserPassenger {

    @EmbeddedId
    private UserPassengerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("passengerId")
    @JoinColumn(name = "passenger_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Passenger passenger;

    public UserPassenger() {
    }

    public UserPassenger(User user, Passenger passenger) {
        this.user = user;
        this.passenger = passenger;
        this.id = new UserPassengerId(user.getId(), passenger.getId());
    }
}