package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buses")
public class Buses {

	@Id
	private int id;
	
	@Column(name = "turn_id")
	private int turnId;
	
	@Column(name = "passenger_car_id")
	private int passengerCarId;
	
	@Column(name = "passenger_num")
	private int passengerNum;
	
	@Column(name = "price")
	private float price;
	
}
