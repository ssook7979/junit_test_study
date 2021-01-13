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
		String pageContent = "There are certain queer times and occasions "
				+ "in this strange mixed affair we call life when a man "
				+ "takes this whole universe for a vast practical joke, "
				+ "though the wit thereof he but dimly discerns, and more "
				+ "than suspects that the joke is at nobody's expense but "
				+ "his own.";
		byte[] bytes = pageContent.getBytes();
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		// search
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
		
		// negative
		URLConnection connection = 
				new URL("http://bit.ly/15sYPA7").openConnection();
		InputStream inputStream = connection.getInputStream();
		search = new Search(inputStream, "smelt", ANY_TITLE);
		search.execute();
		// 추상화: size is equal to 0 보다 is empty가 더 가독성 있음 
		assertThat(search.getMatches()).isEmpty();
		stream.close();

	}

}
