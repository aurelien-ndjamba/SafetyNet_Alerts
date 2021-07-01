package com.safetynets.alerts.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.repository.MedicalRecordRepository;

@SpringBootTest
//@AutoConfigureMockMvc
class CountServicesIT {

//	@Mock
//	private MedicalRecordService medicalRecordService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private PersonService personService;
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	
	@MockBean
	private ReaderJsonService readerJsonService;
	
//	@MockBean
//	private CountService countService;
//	@Autowired
//	private CountService countService;
	
	@Test
	public void testGetAge() throws ParseException {
		assertThat(CountService.getAge("08/06/1945")).isEqualTo((int) 76);
	}
	
	@Test
	public void testGetCountAdult() throws ParseException {
		String firstName = "Eric";
		String lastName = "Cadigan";
		MedicalRecordService medicalRecordService = new MedicalRecordService();
		assertThat(medicalRecordService.findByFirstNameAndLastName(firstName, lastName).getBirthdate()).isEqualTo("08/06/1945");
	}
	
	@Test
	public void testGetCountChildren() throws ParseException {
//		String firstName = "Eric";
//		String lastName = "Cadigan";
//		MedicalRecordService medicalRecordService = new MedicalRecordService();
		assertThat(medicalRecordService.findByFirstNameAndLastName("Eric", "Cadigan").getBirthdate()).isEqualTo("08/06/1945");
	}

}
