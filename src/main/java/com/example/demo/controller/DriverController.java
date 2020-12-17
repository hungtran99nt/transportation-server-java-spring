package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Buses;
import com.example.demo.entity.Driver;
import com.example.demo.entity.Driving;
import com.example.demo.entity.Turn;
import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.model.TurnDto;
import com.example.demo.repository.BusesRepository;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.DrivingRepository;


@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	private DriverRepository driverRepo;
	
	@Autowired
	private BusesRepository busesRepo;

	@Autowired
	private DrivingRepository drivingRepo;

	@GetMapping
	public Iterable<DriverDto> getAllDriver() {
		Iterable<Driver> driverList = driverRepo.findAll();
		return convertToListDriverDto(driverList);
	}

	
	@GetMapping("/search/{key}")
	public Iterable<DriverDto> searchDriver(@PathVariable("key") String key){
		List<Driver> driverList = (List<Driver>) driverRepo.findNameLike(key);
		return convertToListDriverDto(driverList);
	}
	
	@GetMapping("/{id}")
	public DriverDto getDriverById(@PathVariable("id") int id){
		Optional<Driver> driverOptional  = driverRepo.findById(id);
		Driver driver = driverOptional.get();
		return convertToDriverDto(driver);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Driver addDriver(@RequestBody DriverDto driverDto) {
		return driverRepo.save(convertToDriver(driverDto));
		
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteDriver(@PathVariable("id") int id) {
		driverRepo.deleteById(id);
		return true;
	}
	
	@PutMapping("/{id}")
	public Driver updateDriver(@PathVariable("id") int id, @RequestBody DriverDto driverDto) {
		Optional<Driver> driverOptional = driverRepo.findById(id);
		if(driverOptional.isPresent()) {
			Driver driver = driverOptional.get();
			driver.setAddress(driverDto.getAddress());
			driver.setDob(driverDto.getDob());
			driver.setExperience(driverDto.getExperience());
			driver.setId(driverDto.getId());
			driver.setIdentification(driverDto.getIdentification());
			driver.setLisenseId(driverDto.getLisenseId());
			driver.setLisenseType(driverDto.getLisenseType());
			driver.setName(driverDto.getName());
			return driverRepo.save(driver);
		}
		return null;
	}
	
	@GetMapping("/buses-list-by-driver-id")
	public Iterable<BusesDto> getAllBusesByDriverId(@RequestParam Integer driverId) {
		Iterable<Driving> drivingList = drivingRepo.findByDriverId(driverId);
		List<BusesDto> busesList = new ArrayList();
		for(Driving driving : drivingList) {
			busesList.add(convertToBusesDto(busesRepo.findById(driving.getBusesId()).get()));
		}
		return (Iterable<BusesDto>) busesList;
	}
	
	@GetMapping("/driving-list-by-driver-id")
	public Iterable<DrivingDto> getAllDrivingByDriverId(@RequestParam Integer driverId) {
		Iterable<Driving> drivingList = drivingRepo.findByDriverId(driverId);
		return convertToListDrivingDto(drivingList);
	}

	public Iterable<DriverDto> convertToListDriverDto(Iterable<Driver> driverList) {
		if (driverList != null) {
			List<Driver> list = (List<Driver>) driverList;
			return list.stream().map(this::convertToDriverDto).collect(Collectors.toList());
		}
		return null;
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
	
	@GetMapping("/driving-list-by-buses-id")
	public Iterable<DrivingDto> getAllDrivingByBusesId(@RequestParam Integer busesId){
		Iterable<Driving> drivingList = drivingRepo.findByBusesId(busesId);
		return convertToListDrivingDto(drivingList);
		
	}
	
	public DriverDto convertToDriverDto(Driver driver) {
		DriverDto driverDto = new DriverDto();
		if (driverDto != null) {
			driverDto.setAddress(driver.getAddress());
			driverDto.setDob(driver.getDob());
			driverDto.setDrivingList(getAllDrivingByDriverId(driver.getId()));
			driverDto.setExperience(driver.getExperience());
			driverDto.setId(driver.getId());
			driverDto.setIdentification(driver.getIdentification());
			driverDto.setLisenseId(driver.getLisenseId());
			driverDto.setLisenseType(driver.getLisenseType());
			driverDto.setName(driver.getName());
			driverDto.setIsDriver(driver.getIsDriver());
		}
		return driverDto;
	}

	public Driver convertToDriver(DriverDto driverDto) {
		Driver driver = new Driver();
		if(driver != null) {
			driver.setAddress(driverDto.getAddress());
			driver.setDob(driverDto.getDob());
			driver.setExperience(driverDto.getExperience());
			driver.setId(driverDto.getId());
			driver.setIdentification(driverDto.getIdentification());
			driver.setLisenseId(driverDto.getLisenseId());
			driver.setLisenseType(driverDto.getLisenseType());
			driver.setName(driverDto.getName());
			driver.setIsDriver(driverDto.getIsDriver());
		}
		return driver;
	}
	public Iterable<DrivingDto> convertToListDrivingDto(Iterable<Driving> drivingList) {
		if (drivingList != null) {
			List<Driving> list = (List<Driving>) drivingList;
			return list.stream().map(this::convertToDrivingDto).collect(Collectors.toList());
		}
		return null;
	}

	public DrivingDto convertToDrivingDto(Driving driving) {
		DrivingDto drivingDto = new DrivingDto();
		if (drivingDto != null) {
			drivingDto.setBusesId(driving.getBusesId());
			drivingDto.setDriverId(driving.getDriverId());
		}
		return drivingDto;
	}

}
