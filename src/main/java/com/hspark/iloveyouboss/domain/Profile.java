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
	 * **과한 refactoring?**
	 * refactoring 한 메소드들이 3번의 for-loop을 돌고 있음
	 * 성능을 측정하여 크게 문제되는 사항이 아니면 코드의 명확성을 높이는 편이 좋다.
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
