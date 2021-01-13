package com.hspark.iloveyouboss.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import com.hspark.util.Http;

class AddressRetrieverTest {
	@Test
	public void answersAppropriateAddressForValidCoordinates() throws IOException, ParseException {
		Http http = (String url) -> 
		   { 
		     if (!url.contains("lat=38.000000&lon=-104.000000")) 
		        fail("url " + url + " does not contain correct parms");
		     return "{\"address\":{"
		           + "\"house_number\":\"324\","
		           + "\"road\":\"North Tejon Street\","
		           + "\"city\":\"Colorado Springs\","
		           + "\"state\":\"Colorado\","
		           + "\"postcode\":\"80903\","
		           + "\"country_code\":\"us\"}"
		           + "}";
		   };
		
		AddressRetriever retriever = new AddressRetriever(http);
		
		Address address = retriever.retrieve(38.0,-104.0);
		
		assertThat(address.houseNumber).isEqualTo("324");
		assertThat(address.road).isEqualTo("North Tejon Street");
		assertThat(address.city).isEqualTo("Colorado Springs");
		assertThat(address.state).isEqualTo("Colorado");
		assertThat(address.zip).isEqualTo("80903");
	}
	
	@Test
	public void returnsAppropriateAddressForValidCoordinates() throws IOException, ParseException {
	    Http http = new Http() {
	       @Override
	       public String get(String url) throws IOException {
	          return "{\"address\":{"
					 + "\"house_number\":\"324\","
					 + "\"road\":\"North Tejon Street\","
					 // ...
					 + "\"city\":\"Colorado Springs\","
					 + "\"state\":\"Colorado\","
					 + "\"postcode\":\"80903\","
					 + "\"country_code\":\"us\"}"
					 + "}";
	      }};
		AddressRetriever retriever = new AddressRetriever(http);
		
		Address address = retriever.retrieve(38.0,-104.0);
		
		assertThat(address.houseNumber).isEqualTo("324");
		assertThat(address.road).isEqualTo("North Tejon Street");
		assertThat(address.city).isEqualTo("Colorado Springs");
		assertThat(address.state).isEqualTo("Colorado");
		assertThat(address.zip).isEqualTo("80903");
	}
}
