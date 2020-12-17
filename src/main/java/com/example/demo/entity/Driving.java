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
@Table(name = "driving")
public class Driving {
	
	
	@Id
	private int id;
	
	@Column(name = "driver_id")
	private int driverId;
	
	@Column(name = "buses_id")
	private int busesId;
}
