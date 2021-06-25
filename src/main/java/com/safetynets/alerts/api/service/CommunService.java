package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.PersonsByStationNumberModel;
import com.safetynets.alerts.api.model.MedicalRecordDataBaseModel;
import com.safetynets.alerts.api.model.PersonDataBaseModel;
import com.safetynets.alerts.api.model.PersonInfoGlobalModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonInfoAdvanced;
import com.safetynets.alerts.api.repository.FireStationRepository;
import com.safetynets.alerts.api.repository.PersonRepository;

@Service
public class CommunService {

	@Autowired
	private PersonService personService;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private FireStationRepository fireStationRepository;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private CountService countService;

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/childAlert?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public List<ChildInfoModel> getChildInfos(String address) throws ParseException {

		List<ChildInfoModel> ChildInfos = new ArrayList<ChildInfoModel>();
		List<PersonDataBaseModel> persons = new ArrayList<PersonDataBaseModel>();
		persons = personService.getPersonsByAddress(address);

		for (PersonDataBaseModel person : persons) {

			if (countService.getAge(person.getFirstName(), person.getLastName()) <= 18) {

				ChildInfoModel childInfo = new ChildInfoModel();
				childInfo.setId(person.getId());
				childInfo.setFirstName(person.getFirstName());
				childInfo.setLastName(person.getLastName());
				childInfo.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
				childInfo.setFamilyRelationShip(
						personService.getRelationship(address, person.getFirstName(), person.getLastName()));

				ChildInfos.add(childInfo);
			}

		}
		return ChildInfos;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/phoneAlert?firestation=<firestation_number> OK
	// ----------------------------------------------------------------------------------------
	public HashSet<String> getPhoneAlert(Long firestation) {

		HashSet<String> phoneAlert = new HashSet<String>();
		List<FireStationDataBaseModel> fireStations = new ArrayList<FireStationDataBaseModel>();
		fireStations = fireStationService.getFirestationsByStation(firestation);

		for (FireStationDataBaseModel fireStation : fireStations) {
			List<PersonDataBaseModel> persons = new ArrayList<PersonDataBaseModel>();
			persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonDataBaseModel person : persons) {
				phoneAlert.add(person.getPhone());
			}
		}
		return phoneAlert;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/fire?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonInfoAdvanced> getPersonsInfoAdvanced(String address) throws ParseException {

		ArrayList<PersonInfoAdvanced> personsInfoAdvanced = new ArrayList<PersonInfoAdvanced>();
		List<PersonDataBaseModel> persons = new ArrayList<PersonDataBaseModel>();
		persons = personService.getPersonsByAddress(address);

		for (PersonDataBaseModel person : persons) {

			PersonInfoAdvanced PersonInfoAdvanced = new PersonInfoAdvanced();
			PersonInfoAdvanced.setId(person.getId());
			PersonInfoAdvanced.setFirstName(person.getFirstName());
			PersonInfoAdvanced.setLastName(person.getLastName());
			PersonInfoAdvanced.setAddress(person.getAddress());
			PersonInfoAdvanced.setStationNumber(fireStationService.getStationNumberByAddress(address));
			PersonInfoAdvanced.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
			PersonInfoAdvanced.setPhone(person.getPhone());

			MedicalRecordDataBaseModel medicalRecord = new MedicalRecordDataBaseModel();
			medicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(person.getFirstName(),
					person.getLastName());
			PersonInfoAdvanced.setMedications(medicalRecord.getMedications());
			PersonInfoAdvanced.setAllergies(medicalRecord.getAllergies());

			personsInfoAdvanced.add(PersonInfoAdvanced);
		}
		return personsInfoAdvanced;

	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ----------------------------------------------------------------------------------------
	public List<PersonsByStationNumberModel> getPersonsByManyStations(List<Long> stations) throws ParseException {

		List<PersonsByStationNumberModel> personsByManyStationNumber = new ArrayList<PersonsByStationNumberModel>();
		Iterable<FireStationDataBaseModel> fireStationsByManyStations = new ArrayList<FireStationDataBaseModel>();
		fireStationsByManyStations = fireStationService.getFirestationsByManyStation(stations);
		
		for (FireStationDataBaseModel fireStation : fireStationsByManyStations) {

			PersonsByStationNumberModel personsByStationNumber = new PersonsByStationNumberModel();
			personsByStationNumber.setAddress(fireStation.getAddress());
			personsByStationNumber.setPersonsByAddress(personService.getPersonsByAddress(fireStation.getAddress()));

			personsByManyStationNumber.add(personsByStationNumber);
		}
		
		return personsByManyStationNumber;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName> OK
	// ----------------------------------------------------------------------------------------
	public PersonInfoGlobalModel getPersonInfoGlobal(String firstName, String lastName) throws ParseException {

		PersonDataBaseModel person = new PersonDataBaseModel();
		person = personService.getPersonByFirstNameAndLastName(firstName, lastName);

		PersonInfoGlobalModel personInfoGlobal = new PersonInfoGlobalModel();
		personInfoGlobal.setFirstName(person.getFirstName());
		personInfoGlobal.setLastName(person.getLastName());
		personInfoGlobal.setAddress(person.getAddress());
		personInfoGlobal.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
		personInfoGlobal.setEmail(person.getEmail());

		HashSet<String> medicationsByLastName = new HashSet<String>();
		HashSet<String> allergiesByLastName = new HashSet<String>();
		List<MedicalRecordDataBaseModel> medicalRecords = new ArrayList<MedicalRecordDataBaseModel>();
		medicalRecords = medicalRecordService.getMedicalRecordsByLastName(person.getLastName());
		int i = 0;
		int j = 0;
		for (MedicalRecordDataBaseModel medicalRecord : medicalRecords) {

			int countMedications = medicalRecord.getMedications().size();
			while (i < countMedications) {
				medicationsByLastName.add(medicalRecord.getMedications().get(i));
				i++;
			}

			int countAllergies = medicalRecord.getAllergies().size();
			while (j < countAllergies) {
				allergiesByLastName.add(medicalRecord.getAllergies().get(j));
				j++;
			}
			i = 0;
			j = 0;
		}

		personInfoGlobal.setMedications(medicationsByLastName);
		personInfoGlobal.setAllergies(allergiesByLastName);

		return personInfoGlobal;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/communityEmail?city=<city> OK
	// ----------------------------------------------------------------------------------------
	public List<String> getCommunityEmail(String city) {
		List<PersonDataBaseModel> persons = new ArrayList<PersonDataBaseModel>();
		persons = personService.getPersonsByCity(city);
		List<String> CommunityEmail = new ArrayList<String>();
		
		for (PersonDataBaseModel person : persons) {
			CommunityEmail.add(person.getEmail());
			}
		
		return CommunityEmail;
	}

}
