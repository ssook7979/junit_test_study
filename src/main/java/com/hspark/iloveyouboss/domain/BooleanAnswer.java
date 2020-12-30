package com.hspark.iloveyouboss.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooleanAnswer {
	
	private int questionId;
	private Boolean value;
	
}
