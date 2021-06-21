//package com.safetynets.alerts.api.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//import java.text.ParseException;
//import java.util.HashSet;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.safetynets.alerts.api.model.PersonModel;
//import com.safetynets.alerts.api.repository.PersonRepository;
//
//
//public class PersonServiceTest {
//
////	@Mock PersonRepository personRepository;
//	@Mock PersonModel personModel;
////	@Autowired PersonService personService;
//	
//	@Test
//	void testGetFamilyRelationship() throws ParseException {
//		
//		// GIVEN
//		String address = "951 LoneTree Rd";
//		String firstName = "Eric";
//		String lastName = "Cadigan";
//		HashSet<PersonModel> listFamilyRelationship = new HashSet<PersonModel>();
//		
//		// WHEN
//		when(personService.getFamilyRelationship(address, firstName, lastName)).thenReturn(listFamilyRelationship);
//		when(this.personService.getFamilyRelationship(address, firstName, lastName)).thenReturn(new PersonService());
//		PersonService personService = new PersonService();
////		PersonRepository personRepository = new PersonRepository();
//		// THEN
//		assertThat(personService.getFamilyRelationship(address, firstName, lastName)).isEqualTo(listFamilyRelationship);
//	}
//}
