package com.hspark.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static com.hspark.util.ContainsMatches.containsMatches;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class SearchTest {
	// 추상화: 의미가 불분명한 '매직 리터럴' 대신 '상수'를 사용하여 가독성을 높인다.
	private static final String ANY_TITLE = "1";
	
	@Test
	public void testSearch() throws Exception {
		// 테스트 데이터를 생성하는 부분을 추상화한다.
		ByteArrayInputStream stream = streamOn("There are certain queer times and occasions "
				+ "in this strange mixed affair we call life when a man "
				+ "takes this whole universe for a vast practical joke, "
				+ "though the wit thereof he but dimly discerns, and more "
				+ "than suspects that the joke is at nobody's expense but "
				+ "his own.");
		
		Search search = new Search(stream, "practical joke", ANY_TITLE);
		Search.LOGGER.setLevel(Level.OFF);
		search.setSurroundingCharacterCount(10);
		search.execute();
		assertFalse(search.errored());
		// 이 여러개의 assertions는 하나의 assertion으로 축약하여 가독성을 높일 수 있다.
		org.hamcrest.MatcherAssert.assertThat(search.getMatches(), containsMatches(new Match[] { 
		         new Match(ANY_TITLE, "practical joke", 
		                   "or a vast practical joke, though t") }));
		stream.close();
	}
	
	// 여러개의 assertion은 다른 Test로 쪼갠다.
	@Test
	public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
		URLConnection connection = 
				new URL("http://bit.ly/15sYPA7").openConnection();
		InputStream inputStream = connection.getInputStream();
		Search search = new Search(inputStream, "smelt", ANY_TITLE);
		search.execute();
		
		assertThat(search.getMatches()).isEmpty();
		inputStream.close();
	}

	private ByteArrayInputStream streamOn(String pageContent) {
		return new ByteArrayInputStream(pageContent.getBytes());
	}

}
