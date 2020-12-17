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
import com.example.demo.entity.Turn;
import com.example.demo.model.BusesDto;
import com.example.demo.model.DriverDto;
import com.example.demo.model.DrivingDto;
import com.example.demo.model.TurnDto;
import com.example.demo.repository.BusesRepository;
import com.example.demo.repository.DrivingRepository;
import com.example.demo.repository.TurnRepository;


@RestController
@RequestMapping("/turn")
public class TurnController {

	@Autowired
	private TurnRepository turnRepo;
	
	@Autowired
	private  BusesRepository busesRepo;
	
	@Autowired
	private DrivingRepository drivingRepo;
	
	@GetMapping
	public Iterable<TurnDto> getAllTurn(){
		List<Turn> turnList = (List<Turn>)turnRepo.findAll();
		return turnList.stream().map(this::convertToTurnDto).collect(Collectors.toList());
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Turn addTurn(@RequestBody TurnDto turnDto) {
		return turnRepo.save(convertToTurn(turnDto));
		
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTurn(@PathVariable("id") int id) {
		turnRepo.deleteById(id);
		return true;
	}
	
	@PutMapping("/{id}")
	public Turn updateTurn(@PathVariable("id") int id, @RequestBody TurnDto turnDto) {
		Optional<Turn> turnOptional = turnRepo.findById(id);
		if(turnOptional.isPresent()) {
			Turn turn = turnOptional.get();
			turn.setComplicatedLevel(turnDto.getComplicatedLevel());
			turn.setDistance(turnDto.getDistance());
			turn.setEndPlace(turnDto.getEndPlace());
			turn.setStartPlace(turnDto.getStartPlace());
			return turnRepo.save(turn);
		}
		return null;
	}
	
	@GetMapping("/{id}")
	public TurnDto searchTurn(@PathVariable("id") int id){
		Optional<Turn> turnOptional  = turnRepo.findById(id);
		Turn turn = turnOptional.get();
		return convertToTurnDto(turn);
	}
	
	public Turn convertToTurn(TurnDto turnDto) {
		Turn turn = new Turn();
		if(turn != null) {
			turn.setComplicatedLevel(turnDto.getComplicatedLevel());
			turn.setDistance(turnDto.getDistance());
			turn.setEndPlace(turnDto.getEndPlace());
			turn.setStartPlace(turnDto.getStartPlace());
			return turn;
		}
		return null;
	}
	public TurnDto convertToTurnDto(Turn turn) {
		TurnDto turnDto = new TurnDto();
		if(turnDto != null) {
			turnDto.setId(turn.getId());
			turnDto.setComplicatedLevel(turn.getComplicatedLevel());
			turnDto.setDistance(turn.getDistance());
			turnDto.setEndPlace(turn.getEndPlace());
			turnDto.setStartPlace(turn.getStartPlace());
			turnDto.setBusesList(getAllBusesByTurnId(turn.getId()));
		}
		return turnDto;
	}
	
	@GetMapping("/buses-by-turn-id")
	public Iterable<BusesDto> getAllBusesByTurnId(@RequestParam Integer turnId){
		Iterable<Buses> busesList = busesRepo.findByTurnId(turnId);
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
