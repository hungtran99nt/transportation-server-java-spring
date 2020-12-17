package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Buses;

public interface BusesRepository extends JpaRepository<Buses , Integer> {
	
	@Query("SELECT a FROM Buses a WHERE a.turnId = :id")
    Iterable<Buses> findByTurnId(@Param("id") Integer turnId);
	
	@Query("SELECT a FROM Buses a WHERE a.passengerCarId = :id")
    Iterable<Buses> findByPassengerCarId(@Param("id") Integer passengerCarId);

}
