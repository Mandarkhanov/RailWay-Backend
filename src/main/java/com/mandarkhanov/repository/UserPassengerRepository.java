package com.mandarkhanov.repository;

import com.mandarkhanov.model.Passenger;
import com.mandarkhanov.model.UserPassenger;
import com.mandarkhanov.model.UserPassengerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPassengerRepository extends JpaRepository<UserPassenger, UserPassengerId> {
    Optional<UserPassenger> findByUserIdAndPassengerId(Integer userId, Integer passengerId);

    /**
     * Находит всех пассажиров, связанных с конкретным пользователем,
     * используя эффективный JPQL-запрос.
     * @param userId ID пользователя.
     * @return Список пассажиров.
     */
    @Query("SELECT up.passenger FROM UserPassenger up WHERE up.user.id = :userId")
    List<Passenger> findPassengersByUserId(@Param("userId") Integer userId);
}