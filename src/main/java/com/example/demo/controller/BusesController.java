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
import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.repository.BusesRepository;
import com.example.demo.repository.DriverRepository;
import com.example.demo.repository.DrivingRepository;


@RestController
@RequestMapping("/buses")
public class BusesController {

	@Autowired
	private BusesRepository busesRepo;

	@Autowired
	private DrivingRepository drivingRepo;
	
	@Autowired
	private DriverRepository driverRepo;

	@GetMapping
	public Iterable<BusesDto> getAllBuses() {
		Iterable<Buses> busesList = busesRepo.findAll();
		return convertToListBusesDto(busesList);
	}

	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Buses addBuses(@RequestBody BusesDto busesDto) {
		return busesRepo.save(convertToBuses(busesDto));
		
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteBuses(@PathVariable("id") int id) {
		busesRepo.deleteById(id);
		return true;
	}
	
	@GetMapping("/{id}")
	public BusesDto searchBuses(@PathVariable("id") int id){
		Optional<Buses> busesOptional  = busesRepo.findById(id);
		Buses buses = busesOptional.get();
		return convertToBusesDto(buses);
	}
	
	@PutMapping("/{id}")
	public Buses updateBuses(@PathVariable("id") int id, @RequestBody BusesDto busesDto) {
		Optional<Buses> busesOptional = busesRepo.findById(id);
		if(busesOptional.isPresent()) {
			Buses buses = busesOptional.get();
			buses.setId(busesDto.getId());
			buses.setPassengerCarId(busesDto.getPassengerCarId());
			buses.setPassengerNum(busesDto.getPassengerNum());
			buses.setPrice(busesDto.getPrice());
			buses.setTurnId(busesDto.getTurnId());
			return busesRepo.save(buses);
		}
		return null;
	}
	
	@GetMapping("/driver-list-by-buses-id")
	public Iterable<DriverDto> getAllDrivergByBusesId(@RequestParam Integer busesId) {
		Iterable<Driving> drivingList = drivingRepo.findByBusesId(busesId);
		List<DriverDto> driverList = new ArrayList();
		for(Driving driving : drivingList) {
			driverList.add(convertToDriverDto(driverRepo.findById(driving.getDriverId()).get()));
		}
		return (Iterable<DriverDto>) driverList;

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
	
	@GetMapping("/driving-list-by-driver-id")
	public Iterable<DrivingDto> getAllDrivingByDriverId(@RequestParam Integer driverId) {
		Iterable<Driving> drivingList = drivingRepo.findByDriverId(driverId);
		return convertToListDrivingDto(drivingList);
	}

	
	public Buses convertToBuses(BusesDto busesDto) {
		Buses buses = new Buses();
		if(buses != null) {
			buses.setId(busesDto.getId());
			buses.setPassengerCarId(busesDto.getPassengerCarId());
			buses.setPassengerNum(busesDto.getPassengerNum());
			buses.setPrice(busesDto.getPrice());
			buses.setTurnId(busesDto.getTurnId());
			return buses;

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

	
	public BusesDto convertToBusesDto(Buses buses) {
		BusesDto busesDto = new BusesDto();
		if (busesDto != null) {
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
	public Iterable<DrivingDto> getAllDrivingByBusesId(@RequestParam Integer busesId) {
		Iterable<Driving> drivingList = drivingRepo.findByBusesId(busesId);
		return convertToListDrivingDto(drivingList);

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
