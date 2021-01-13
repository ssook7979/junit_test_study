package com.hspark.iloveyouboss.domain;

import java.util.Map;

/*
 * Profile class로부터 score를 계산하는 로직을 분리한다.
 */
public class MatchSet {
	private Map<String, Answer> answers;
	private int score = 0;
	private Criteria criteria;

	public MatchSet(Map<String, Answer> answers, Criteria criteria) {
		this.answers = answers;
		this.criteria = criteria;
		calculateScore(criteria);
	}

	private void calculateScore(Criteria criteria) {
	   for (Criterion criterion: criteria) 
	      if (criterion.matches(answerMatching(criterion))) 
	         score += criterion.getWeight().getValue();
	}
	
	private Answer answerMatching(Criterion criterion) {
	   return answers.get(criterion.getAnswer().getQuestionText());
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean matches() {
		if (doesNotMeetAnyMustMatchCriterion(criteria))
		      return false;
		   return anyMatches(criteria);		
	}
	
	private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
		for (Criterion criterion: criteria) {
		   boolean match = criterion.matches(answerMatching(criterion));
		   if (!match && criterion.getWeight() == Weight.MustMatch) 
		      return true;
		}
		return false;
	}
	
	private boolean anyMatches(Criteria criteria) {
		boolean anyMatches = false;
		for (Criterion criterion: criteria) 
		   anyMatches |= criterion.matches(answerMatching(criterion));
		return anyMatches;
	}
}