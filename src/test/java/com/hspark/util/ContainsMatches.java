package com.hspark.util;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

class ContainsMatches extends TypeSafeMatcher<List<Match>>{
	
	private Match[] expected;

	public ContainsMatches(Match[] expected) {
		this.expected = expected;
	}
	
	private boolean equals(Match expected, Match actual) {
		return expected.searchString.equals(actual.searchString) 
				&& expected.surroundingContext.equals(actual.surroundingContext);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("<" + expected.toString() + ">");
	}

	@Override
	protected boolean matchesSafely(List<Match> actual) {
		if (actual.size() != expected.length)
		    return false;
		for (int i = 0; i < expected.length; i++)
			if (!equals(expected[i], actual.get(i)))
				return false;
		return true;
	}

	public static <T> Matcher<List<Match>> containsMatches(Match[] expected) {
		return new ContainsMatches(expected);
	}
}
