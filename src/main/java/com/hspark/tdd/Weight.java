package com.hspark.tdd;

public enum Weight {
	DontCare(0), Important(100), MustMatch(1000);
	
	private int score;
	
	private Weight(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
