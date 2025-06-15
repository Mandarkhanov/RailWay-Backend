package com.mandarkhanov.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPassengerId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "passenger_id")
    private Integer passengerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPassengerId that = (UserPassengerId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(passengerId, that.passengerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, passengerId);
    }
}