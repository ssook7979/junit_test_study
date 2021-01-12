package com.hspark.iloveyouboss.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
	
	private Map<String, Answer> answers = new HashMap<>();
	private int score;
	private String name;

	public Profile(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}
	
	/*
	 * ** matches 메소드 작동 알고리즘 **
	 * - calculateScore: 주어진 조건에 따라 점수를 계산
	 * - doesNotMeetAnyMustMatchCriterion: 어떤 필수조건을 만족하지 않으면 false 반환
	 * - anyMatches: 어떤 조건에 적어도 한개라도 만족하는지 여부를 반환
	 * 
	 * 리팩토링 후 우려할 만한 성능의 저하가 있는지 여부를 테스트하여 리팩토링 할지를 결정한다.
	 */
	public boolean matches(Criteria criteria) {
		calculateScore(criteria);
		
		if(doesNotMeetAnyMustMatchCriterion(criteria)) {
			return false; 
		}
		return anyMatches(criteria);
	}

	private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
		for (Criterion criterion: criteria) {
			boolean match = criterion.matches(answerMatching(criterion));
			if (!match && criterion.getWeight() == Weight.MustMatch) {
				return true;
			}	
		}
		return true;
	}

	private void calculateScore(Criteria criteria) {
		for (Criterion criterion: criteria) {
			score = 0;
			
			if (criterion.matches(answerMatching(criterion))) {
				score += criterion.getWeight().getValue();
			}	
		}
	}

	private boolean anyMatches(Criteria criteria) {
		boolean anyMatches = false;
		for (Criterion criterion: criteria) {
			anyMatches |= criterion.matches(answerMatching(criterion));
		}
		return anyMatches;
	}

	private Answer answerMatching(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}
	
	public int score() {
		return score;
	}

	public List<Answer> classicFind(Predicate<Answer> pred) {
	   List<Answer> results = new ArrayList<Answer>();
	   for (Answer answer: answers.values())
	      if (pred.test(answer))
	         results.add(answer);
	   return results;
	}
	
	@Override
	public String toString() {
	  return name;
	}
	
	public List<Answer> find(Predicate<Answer> pred) {
	   return answers.values().stream()
	         .filter(pred)
	         .collect(Collectors.toList());
	}
}
