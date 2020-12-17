package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Buses;
import com.example.demo.entity.Driver;
import com.example.demo.entity.Driving;
import com.example.demo.entity.PassengerCar;
import com.example.demo.entity.Turn;
import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.repository.BusesRepository;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.DrivingRepository;
import com.example.demo.repository.PassengerCarRepository;
import com.example.demo.repository.TurnRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/design")
public class DesignController {

	@Autowired
	private  DriverRepository driverRepo;
	
	@Autowired
	private  BusesRepository busesRepo;
	
	@Autowired
	private PassengerCarRepository passengerCarRepo;
	
	@Autowired
	private TurnRepository turnRepo;
	
	@Autowired
	private DrivingRepository drivingRepo;
	
	@GetMapping("/driver")
	public Iterable<DriverDto> getAllDriver(){
		Iterable<Driver> driverList = driverRepo.findAll();
		return convertToListDriverDto(driverList);
	}
	
	@GetMapping("/passenger-car")
	public Iterable<PassengerCar> getAllPassengerCar(){
		Iterable<PassengerCar> passengerCarList = passengerCarRepo.findAll();
		return passengerCarList;
	}
	
	
	@GetMapping("/turn")
	public Iterable<Turn> getAllTurn(){
		Iterable<Turn> turnList = turnRepo.findAll();
		return turnList;
	}
	
	@GetMapping("/driving")
	public Iterable<DrivingDto> getAllDriving(){
		Iterable<Driving> drivingList = drivingRepo.findAll();
		return convertToListDrivingDto(drivingList);
	}
	
	@GetMapping("/buses")
	public Iterable<BusesDto> getAllBuses(){
		Iterable<Buses> busesList = busesRepo.findAll();
		return convertToListBusesDto(busesList);
	}
	
	@GetMapping("/buses-by-turn-id")
	public Iterable<BusesDto> getAllBusesByTurnId(@RequestParam Integer turnId){
		Iterable<Buses> busesList = busesRepo.findByTurnId(turnId);
		return convertToListBusesDto(busesList);
	}
	
	@GetMapping("/driving-list-by-buses-id")
	public Iterable<DrivingDto> getAllDrivingByBusesId(@RequestParam Integer busesId){
		Iterable<Driving> drivingList = drivingRepo.findByBusesId(busesId);
		return convertToListDrivingDto(drivingList);
		
	}
	
	@GetMapping("/driving-list-by-driver-id")
	public Iterable<DrivingDto> getAllDrivingByDriverId(@RequestParam Integer driverId){
		Iterable<Driving> drivingList = drivingRepo.findByDriverId(driverId);
		return convertToListDrivingDto(drivingList);
	}
	
	public DrivingDto convertToDrivingDto(Driving driving) {
		DrivingDto drivingDto = new DrivingDto();
		if(drivingDto != null) {
			drivingDto.setBusesId(driving.getBusesId());
			drivingDto.setDriverId(driving.getDriverId());
		}
		return drivingDto;
	}
	
	public BusesDto convertToBusesDto(Buses buses) {
		BusesDto busesDto = new BusesDto();
		if(busesDto != null) {
			busesDto.setId(buses.getId());
			busesDto.setPassengerCarId(buses.getPassengerCarId());
			busesDto.setPassengerNum(buses.getPassengerNum());
			busesDto.setPrice(buses.getPrice());
			busesDto.setTurnId(buses.getTurnId());
			busesDto.setListDriving(getAllDrivingByBusesId(buses.getId()));
		}
		return busesDto;
	}
	
	public DriverDto convertToDriverDto(Driver driver) {
		DriverDto driverDto = new DriverDto();
		if(driverDto != null) {
			driverDto.setAddress(driver.getAddress());
			driverDto.setDob(driver.getDob());
			driverDto.setDrivingList(getAllDrivingByDriverId(driver.getId()));
			driverDto.setExperience(driver.getExperience());
			driverDto.setId(driver.getId());
			driverDto.setIdentification(driver.getIdentification());
			driverDto.setLisenseId(driver.getLisenseId());
			driverDto.setLisenseType(driver.getLisenseType());
			driverDto.setName(driver.getName());
		}
		return driverDto;
	}
	
	
	public Iterable<DrivingDto> convertToListDrivingDto(Iterable<Driving> drivingList){
		if(drivingList != null) {
			List<Driving> list = (List<Driving>) drivingList;
			return list.stream().map(this::convertToDrivingDto).collect(Collectors.toList());
		}
		return null;
	}
	
	 private Iterable<BusesDto> convertToListBusesDto(Iterable<Buses> busesList) {
	        if (busesList != null) {
	            List<Buses> list = (List<Buses>) busesList;
	            return list.stream().map(this::convertToBusesDto).collect(Collectors.toList());
	        }
	        return null;
	    }
	 
	 public Iterable<DriverDto> convertToListDriverDto(Iterable<Driver> driverList){
		 if(driverList != null) {
			 List<Driver> list = (List<Driver>) driverList;
			 return list.stream().map(this::convertToDriverDto).collect(Collectors.toList());
		 }
		 return null;
	 }
	
}
