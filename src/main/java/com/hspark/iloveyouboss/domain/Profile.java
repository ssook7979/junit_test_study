package com.hspark.iloveyouboss.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Profile class의 책임
 *  - 프로파일에 관한 정보 추적하기
 *  - 조건 집합이 프로파일에 매칭되는지 혹은 그 정도 파악하기
 *  
 * => 단일책임 원칙을 위배한다.
 * => 클래스를 책임 단위로 분할한다.
 */
public class Profile {
	private Map<String,Answer> answers = new HashMap<>();
	
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
	
	public boolean matches(Criteria criteria) {
		MatchSet matchSet = new MatchSet(answers, criteria);
		score = matchSet.getScore();
		return matchSet.matches();
	}
	
	// 중복코드
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