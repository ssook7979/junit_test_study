package com.hspark.iloveyouboss.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
	// code smell: shotgun surgery(기능의 산재)
	private Map<String,Answer> answers = new HashMap<>();
	
	/*
	 * Profile과 MatchSet은 같은 reference를 멤버변수로 사용하고 있음
	 * 기능이 복수의 클래스에 흩어져 있으므로 유지 보수가 쉽지 않다.
	 * 또한, 한 클래스에서 상태를 변경할 수 있으므로 결함을 일으킬 가능성이 있다.
	 */
	
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
	
	
	public MatchSet getMatchSet(Criteria criteria) {
		return new MatchSet(answers, criteria);
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