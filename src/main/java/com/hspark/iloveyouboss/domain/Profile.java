package com.hspark.iloveyouboss.domain;

public class Profile {
	/*
	 * code smell: shotgun surgery(기능의 산재)
	 * => answer과 관련된 로직을 AnswerCollection에 캡슐화
	 */
	private AnswerCollection answers = new AnswerCollection();
	private String name;

	public Profile(String name) {
	   this.name = name;
	}
	
	public String getName() {
	   return name;
	}
	
	public void add(Answer answer) {
	   answers.add(answer);
	}
	
	public MatchSet getMatchSet(Criteria criteria) {
		return new MatchSet(answers, criteria);
	}

	@Override
	public String toString() {
	  return name;
	}
}