package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootTest
public class ReaderJsonServiceTest {

	@Test
	public void testFindListPersons() throws ParseException, JsonParseException, JsonMappingException, IOException {
		assertThat(ReaderJsonService.findListPersons("src/main/resources/data.json").size()).isEqualTo(23);
	}

	@Test
	public void testFindListFireStations()
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		assertThat(ReaderJsonService.findListFireStations("src/main/resources/data.json").size()).isEqualTo(12);
	}

	@Test
	public void testFindListMedicalRecords()
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		assertThat(ReaderJsonService.findListMedicalRecords("src/main/resources/data.json").size()).isEqualTo(23);
	}

}
