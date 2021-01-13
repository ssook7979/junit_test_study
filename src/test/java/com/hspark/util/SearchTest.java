package com.hspark.util;

import static org.assertj.core.api.Assertions.assertThat;
import static com.hspark.util.ContainsMatches.containsMatches;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchTest {
	// 추상화: 의미가 불분명한 '매직 리터럴' 대신 '상수'를 사용하여 가독성을 높인다.
	private static final String ANY_TITLE = "1";
	private InputStream stream;
	
	@BeforeEach
	void turnOffLogging() {
		Search.LOGGER.setLevel(Level.OFF);
	}
	
	@AfterEach
	void closeResources() throws Exception {
		stream.close();
	}
	
	@Test
	public void returnsMatchesShowingContextWhenSearchStringInContent() throws Exception {
		stream = streamOn("rest of text here"
	            + "1234567890search term1234567890"
	            + "more rest of text");		
		Search search = new Search(stream, "search term", ANY_TITLE);	
		search.setSurroundingCharacterCount(10);
		
		search.execute();
		
		org.hamcrest.MatcherAssert.assertThat(search.getMatches(), containsMatches(new Match[] { 
		         new Match(ANY_TITLE, 
		        		 "search term", 
		                 "1234567890search term1234567890") }));		
	}
	
	@Test
	public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
		stream = streamOn("any text");
		Search search = new Search(stream, "text that doesn't match", ANY_TITLE);
		
		search.execute();
		
		assertThat(search.getMatches()).isEmpty();
	}
	
	// 이 assertion은 지금 당장 필요하지 않지만 추후에 다른 테스트에 사용해야 하므로 메모...
	// TODO:
	@Test
	public void noErrorOccurred() {
		// assertFalse(search.errored());
	}

	private ByteArrayInputStream streamOn(String pageContent) {
		return new ByteArrayInputStream(pageContent.getBytes());
	}

}
