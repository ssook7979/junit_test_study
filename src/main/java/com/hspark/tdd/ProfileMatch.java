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
	
	public void update(Weight weight, boolean match) {
		updateMatch(match);
		if (match) {
			updateScore(weight.getScore());
		}
	}
	
	private void updateMatch(boolean match) {
		this.match |= match;
	}
	
	private void updateScore(int score) {
		this.score += score;
	}
	
	public void setMatch(boolean match) {
		this.match = match;
	}

}
