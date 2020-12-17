package com.example.demo.entity;

import java.util.Date;

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
@Table(name = "passenger_car")
public class PassengerCar {
	
	@Id
	private int id;
	
	@Column(name = "number_plate")
	private String numberPlate;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "capacity")
	private int capacity;
	
	@Column(name = "year_num")
	private int yearNum;
	
	@Column(name = "last_repair_day")
	private Date lastRepairDay;
	
}
