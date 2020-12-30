package com.hspark.iloveyouboss.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value="boolean")
@NoArgsConstructor
public class BooleanQuestion extends Question {

	private static final long serialVersionUID = 1L;
	private static final List<String> answerChoices 
			= Arrays.asList(new String[] { "No", "Yes" });

	public BooleanQuestion(String text) {
		super(text);
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected == actual;
	}

	@Override
	public List<String> getAnswerChoices() {
		return answerChoices;
	}
	

}
