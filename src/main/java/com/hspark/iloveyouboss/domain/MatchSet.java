package com.hspark.iloveyouboss.domain;

/*
 * Profile class로부터 score를 계산하는 로직을 분리한다.
 */
public class MatchSet {
	private AnswerCollection answers;
	private Criteria criteria;

	public MatchSet(AnswerCollection answers, Criteria criteria) {
		this.answers = answers;
		this.criteria = criteria;
	}
	
	/*
	 * score는 상태변수로 둘 필요가 없으므로 필요할 때마다 계산할 수 있도록 한다.
	 * 문제점
	 * - 매번 계산하는 것은 성능저하를 일으키는가?
	 * 	 => 지연 초기화(lazy initialization) 고려
	 * 
	 */
	public int getScore() {
		int score = 0;
		for (Criterion criterion: criteria) 
		   if (criterion.matches(answers.answerMatching(criterion))) 
		      score += criterion.getWeight().getValue();
		return score;
	}
	
	public boolean matches() {
		if (doesNotMeetAnyMustMatchCriterion(criteria))
		      return false;
		   return anyMatches(criteria);		
	}
	
	private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
		for (Criterion criterion: criteria) {
		   boolean match = criterion.matches(answers.answerMatching(criterion));
		   if (!match && criterion.getWeight() == Weight.MustMatch) 
		      return true;
		}
		return false;
	}
	
	private boolean anyMatches(Criteria criteria) {
		boolean anyMatches = false;
		for (Criterion criterion: criteria) 
		   anyMatches |= criterion.matches(answers.answerMatching(criterion));
		return anyMatches;
	}
}