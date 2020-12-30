package com.hspark.iloveyouboss.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weight {
	MustMatch(Integer.MAX_VALUE),
	VeryImportant(5000),
	Important(1000),
	WouldPrefer(100),
	DontCare(0);
	
	private int value;
}
