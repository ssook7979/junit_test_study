package com.hspark.tdd;

public class BooleanQuestion implements Question {

	private int id;
	private String text;

	public BooleanQuestion(int id, String text) {
		this.id = id;
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

}
