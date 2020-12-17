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
@Table(name = "driver")
public class Driver {
	
	@Id
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "identification")
	private String identification;
	
	@Column(name = "lisense_id")
	private String lisenseId;
	
	@Column(name = "lisense_type")
	private String lisenseType;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "experience")
	private int experience;
	
	@Column(name = "is_driver")
	private int isDriver;
}
