package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnDto {

	private int id;
	private String startPlace;	
	private String endPlace;
	private float distance;	
	private int complicatedLevel;
	private Iterable<BusesDto> busesList;
}
