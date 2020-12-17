package com.example.demo.controller;

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
import com.example.demo.entity.PassengerCar;
import com.example.demo.entity.Turn;
import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.model.PassengerCarDto;
import com.example.demo.model.TurnDto;
import com.example.demo.repository.BusesRepository;
import com.example.demo.repository.DrivingRepository;
import com.example.demo.repository.PassengerCarRepository;

@RestController
@RequestMapping("/passenger-car")
public class PassengerCarController {
	
	@Autowired
	private PassengerCarRepository passengerCarRepo;
	
	@Autowired
	private BusesRepository busesRepo;
	
	@Autowired
	private DrivingRepository drivingRepo;
	
	@GetMapping
	public Iterable<PassengerCarDto> getAllPassengerCar() {
		Iterable<PassengerCar> passengerCarList = passengerCarRepo.findAll();
		return convertToListPassengerCarDto(passengerCarList);
	}


	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public PassengerCar addPassengerCar(@RequestBody PassengerCarDto passengerCarDto) {
		return passengerCarRepo.save(convertToPassengerCar(passengerCarDto));
		
	}
	
	@DeleteMapping("/{id}")
	public boolean deletePassengerCar(@PathVariable("id") int id) {
		passengerCarRepo.deleteById(id);
		return true;
	}
	
	@GetMapping("search/{id}")
	public PassengerCarDto searchPassengerCar(@PathVariable("id") int id){
		Optional<PassengerCar> passengerOptional  = passengerCarRepo.findById(id);
		PassengerCar passengerCar = passengerOptional.get();
		return convertToPassengerCarDto(passengerCar);
	}
	
	@GetMapping("/{id}")
	public PassengerCarDto getPassengerCarById(@PathVariable("id") int id){
		Optional<PassengerCar> passengerCarOptional  = passengerCarRepo.findById(id);
		PassengerCar passengerCar = passengerCarOptional.get();
		return convertToPassengerCarDto(passengerCar);
	}
	
	@PutMapping("/{id}")
	public PassengerCar updatePassengerCar(@PathVariable("id") int id, @RequestBody PassengerCarDto passengerCarDto) {
		Optional<PassengerCar> passengerCarOptional = passengerCarRepo.findById(id);
		if(passengerCarOptional.isPresent()) {
			PassengerCar passengerCar = passengerCarOptional.get();
			passengerCar.setCapacity(passengerCarDto.getCapacity());
			passengerCar.setColor(passengerCarDto.getColor());
			passengerCar.setId(passengerCarDto.getId());
			passengerCar.setLastRepairDay(passengerCarDto.getLastRepairDay());
			passengerCar.setManufacturer(passengerCarDto.getManufacturer());
			passengerCar.setModel(passengerCarDto.getModel());
			passengerCar.setNumberPlate(passengerCarDto.getNumberPlate());
			passengerCar.setYearNum(passengerCarDto.getYearNum());
			return passengerCarRepo.save(passengerCar);
		}
		return null;
	}
	
	public PassengerCar convertToPassengerCar(PassengerCarDto passengerCarDto) {
		PassengerCar passengerCar = new PassengerCar();
		if(passengerCar != null) {
			passengerCar.setCapacity(passengerCarDto.getCapacity());
			passengerCar.setColor(passengerCarDto.getColor());
			passengerCar.setId(passengerCarDto.getId());
			passengerCar.setLastRepairDay(passengerCarDto.getLastRepairDay());
			passengerCar.setManufacturer(passengerCarDto.getManufacturer());
			passengerCar.setModel(passengerCarDto.getModel());
			passengerCar.setNumberPlate(passengerCarDto.getNumberPlate());
			passengerCar.setYearNum(passengerCarDto.getYearNum());
			return passengerCar;
		}
		return null;
	}
	public Iterable<PassengerCarDto> convertToListPassengerCarDto(Iterable<PassengerCar> passengerCarList) {
		// TODO Auto-generated method stub
		if (passengerCarList != null) {
			List<PassengerCar> list = (List<PassengerCar>) passengerCarList;
			return list.stream().map(this::convertToPassengerCarDto).collect(Collectors.toList());
		}
		return null;
	}
	
	public PassengerCarDto convertToPassengerCarDto(PassengerCar passengerCar) {
		PassengerCarDto passengerCarDto = new PassengerCarDto();
		if(passengerCarDto != null) {
			passengerCarDto.setCapacity(passengerCar.getCapacity());
			passengerCarDto.setColor(passengerCar.getColor());
			passengerCarDto.setId(passengerCar.getId());
			passengerCarDto.setLastRepairDay(passengerCar.getLastRepairDay());
			passengerCarDto.setManufacturer(passengerCar.getManufacturer());
			passengerCarDto.setModel(passengerCar.getModel());
			passengerCarDto.setNumberPlate(passengerCar.getNumberPlate());
			passengerCarDto.setYearNum(passengerCar.getYearNum());
			passengerCarDto.setBusesList(getAllBusesByPassengerCarId(passengerCar.getId()));
			return passengerCarDto;
		}
		return null;
	}
	
	@GetMapping("/buses-by-passenger-car-id")
	public Iterable<BusesDto> getAllBusesByPassengerCarId(@RequestParam Integer passengerCarId){
		Iterable<Buses> busesList = busesRepo.findByPassengerCarId(passengerCarId);
		return convertToListBusesDto(busesList);
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
	
	public Iterable<DrivingDto> convertToListDrivingDto(Iterable<Driving> drivingList){
		if(drivingList != null) {
			List<Driving> list = (List<Driving>) drivingList;
			return list.stream().map(this::convertToDrivingDto).collect(Collectors.toList());
		}
		return null;
	}
	
	public DrivingDto convertToDrivingDto(Driving driving) {
		DrivingDto drivingDto = new DrivingDto();
		if(drivingDto != null) {
			drivingDto.setBusesId(driving.getBusesId());
			drivingDto.setDriverId(driving.getDriverId());
		}
		return drivingDto;
	}
}
