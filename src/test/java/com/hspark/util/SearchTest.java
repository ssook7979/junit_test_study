package com.hspark.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class SearchTest {

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
		Search search = new Search(stream, "practical joke", "1");
		Search.LOGGER.setLevel(Level.OFF);
		search.setSurroundingCharacterCount(10);
		search.execute();
		assertFalse(search.errored());
		List<Match> matches = search.getMatches();
		assertThat(matches).isNotNull();
		assertTrue(matches.size() >= 1);
		Match match = matches.get(0);
		assertThat(match.searchString).isEqualTo("practical joke");
		assertThat(match.surroundingContext).isEqualTo("or a vast practical joke, though t");
		stream.close();
		
		// negative
		URLConnection connection = 
				new URL("http://bit.ly/15sYPA7").openConnection();
		InputStream inputStream = connection.getInputStream();
		search = new Search(inputStream, "smelt", "http://bit.ly/15sYPA7");
		search.execute();
		assertThat(search.getMatches().size()).isEqualTo(0);
		stream.close();

	}

}
