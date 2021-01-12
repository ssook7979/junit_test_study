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
	
	// Criterion과 관련된 메소드이므로 Criterion으로 옮긴다.
	public boolean matches(Answer answer) {
		boolean match = getWeight() == Weight.DontCare ||
				answer.match(getAnswer());
		return match;
	}

}
