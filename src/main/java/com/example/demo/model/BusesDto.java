package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusesDto {

	private int id;
	private int turnId;
	private int passengerCarId;
	private int passengerNum;
	private float price;
	private Iterable<DrivingDto> listDriving;
}
