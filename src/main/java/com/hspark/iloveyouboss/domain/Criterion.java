package com.hspark.iloveyouboss.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criterion {

	private Answer answer;
	private Weight weight;

	public Criterion(Answer answer, Weight weight) {
		this.answer = answer;
		this.weight = weight;
	}

}
