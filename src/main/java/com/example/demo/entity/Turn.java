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
@Table(name = "turn")
public class Turn {
	
	@Id
	private int id;
	
	@Column(name = "start_place")
	private String startPlace;
	
	@Column(name = "end_place")
	private String endPlace;
	
	@Column(name = "distance")
	private float distance;
	
	@Column(name = "complicated_level")
	private int complicatedLevel;
}
