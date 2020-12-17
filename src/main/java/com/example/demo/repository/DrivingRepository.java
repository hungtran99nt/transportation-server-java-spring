package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Buses;
import com.example.demo.entity.Driving;


public interface DrivingRepository extends JpaRepository<Driving , Integer> {

	
	@Query("SELECT a FROM Driving a WHERE a.driverId = :id")
    Iterable<Driving> findByDriverId(@Param("id") Integer driverId);
	
	@Query("SELECT a FROM Driving a WHERE a.busesId = :id")
    Iterable<Driving> findByBusesId(@Param("id") Integer busesId);

}
