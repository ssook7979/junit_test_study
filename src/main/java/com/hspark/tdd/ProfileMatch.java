package com.hspark.tdd;

public class ProfileMatch {

	private int score;
	private boolean match;
	
	public int getScore() {
		return score;
	}
	
	public boolean isMatch() {
		return match;
	}
	
	public void updateMatch(boolean match) {
		this.match |= match;
	}
	
	public void setMatch(boolean match) {
		this.match = match;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}
