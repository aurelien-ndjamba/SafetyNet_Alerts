package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountServiceIT {

	@Autowired
	private CountService countService;
	
	@Test
	public void testGetCountAdult() throws ParseException {
		assertThat(countService.findCountAdult(3)).isEqualTo(10);
	}

	@Test
	public void testGetCountChildren() throws ParseException {
		assertThat(countService.findCountChildren(3)).isEqualTo(3);
	}

}
