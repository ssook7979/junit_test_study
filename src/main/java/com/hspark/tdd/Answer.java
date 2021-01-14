package com.hspark.tdd;

public class Answer {

	private Question question;
	private Bool bool;

	public Answer(Question question, Bool answer) {
		this.question = question;
		this.bool = answer;	
	}

	public boolean match(Answer answer) {
		if (answer != null) {
			return bool == answer.bool;			
		}
		return false;
	}

	public String getQuestionText() {
		return question.getText();
	}

}
