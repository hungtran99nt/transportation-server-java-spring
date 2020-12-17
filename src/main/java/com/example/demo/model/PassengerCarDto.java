package com.example.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerCarDto {
	private int id;
	private String numberPlate;
	private String color;
	private String manufacturer;
	private String model;
	private int capacity;
	private int yearNum;
	private Date lastRepairDay;
	private Iterable<BusesDto> busesList;
}
