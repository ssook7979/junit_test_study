package com.hspark.iloveyouboss;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class Question {
	
	private int id;
	private String text;
	private String[] answerChoices;
	
	public String getAnswerChoice(int i) {
		return answerChoices[i];
	}
	
	public boolean match(Answer answer) {
		return false;
	}
	
	abstract public boolean match(int expected, int actual);
	
	public int indexOf(String matchingAnswerChoice) {
		for (int i = 0; i < answerChoices.length; i++) {
			if (answerChoices[i].equals(matchingAnswerChoice)) {
				return i;
			}
		}
		return -1;
	}
	
}
