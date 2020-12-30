package com.hspark.iloveyouboss.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value="percentile")
@NoArgsConstructor
public class PercentileQuestion extends Question {
	
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	@CollectionTable(name="AnswerChoice",
					joinColumns=@JoinColumn(name="question_id"))
	private List<String> answerChoices;

	public PercentileQuestion(String text, String[] answerChoices) {
		super(text);
		this.answerChoices = Arrays.asList(answerChoices);
	}
	
	@Override
	public List<String> getAnswerChoices() {
		return answerChoices;
	}

	@Override
	public boolean match(int expected, int actual) {
		return expected <= actual;
	}

}
