package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonsByStationNumberModel;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonInfoGlobalModel;
import com.safetynets.alerts.api.model.PersonInfoAdvancedModel;

@Service
public class CommunService {

	@Autowired
	private PersonService personService;
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private CountService countService;

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/childAlert?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public List<ChildInfoModel> getChildInfos(String address) throws ParseException {

		List<ChildInfoModel> ChildInfos = new ArrayList<ChildInfoModel>();
		List<PersonModel> persons = new ArrayList<PersonModel>();
		persons = personService.getPersonsByAddress(address);

		for (PersonModel person : persons) {

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
		List<FireStationModel> fireStations = new ArrayList<FireStationModel>();
		fireStations = fireStationService.getFirestationsByStation(firestation);

		for (FireStationModel fireStation : fireStations) {
			List<PersonModel> persons = new ArrayList<PersonModel>();
			persons = personService.getPersonsByAddress(fireStation.getAddress());
			for (PersonModel person : persons) {
				phoneAlert.add(person.getPhone());
			}
		}
		return phoneAlert;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/fire?address=<address> OK
	// ----------------------------------------------------------------------------------------
	public ArrayList<PersonInfoAdvancedModel> getPersonsInfoAdvanced(String address) throws ParseException {

		ArrayList<PersonInfoAdvancedModel> personsInfoAdvanced = new ArrayList<PersonInfoAdvancedModel>();
		List<PersonModel> persons = new ArrayList<PersonModel>();
		persons = personService.getPersonsByAddress(address);

		for (PersonModel person : persons) {

			PersonInfoAdvancedModel PersonInfoAdvanced = new PersonInfoAdvancedModel();
			PersonInfoAdvanced.setId(person.getId());
			PersonInfoAdvanced.setFirstName(person.getFirstName());
			PersonInfoAdvanced.setLastName(person.getLastName());
			PersonInfoAdvanced.setAddress(person.getAddress());
			PersonInfoAdvanced.setStationNumber(fireStationService.getStationNumberByAddress(address));
			PersonInfoAdvanced.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
			PersonInfoAdvanced.setPhone(person.getPhone());

			MedicalRecordModel medicalRecord = new MedicalRecordModel();
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
		Iterable<FireStationModel> fireStationsByManyStations = new ArrayList<FireStationModel>();
		fireStationsByManyStations = fireStationService.getFirestationsByManyStation(stations);
		
		for (FireStationModel fireStation : fireStationsByManyStations) {

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

		PersonModel person = new PersonModel();
		person = personService.getPersonByFirstNameAndLastName(firstName, lastName);

		PersonInfoGlobalModel personInfoGlobal = new PersonInfoGlobalModel();
		personInfoGlobal.setFirstName(person.getFirstName());
		personInfoGlobal.setLastName(person.getLastName());
		personInfoGlobal.setAddress(person.getAddress());
		personInfoGlobal.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
		personInfoGlobal.setEmail(person.getEmail());

		HashSet<String> medicationsByLastName = new HashSet<String>();
		HashSet<String> allergiesByLastName = new HashSet<String>();
		List<MedicalRecordModel> medicalRecords = new ArrayList<MedicalRecordModel>();
		medicalRecords = medicalRecordService.getMedicalRecordsByLastName(person.getLastName());
		int i = 0;
		int j = 0;
		for (MedicalRecordModel medicalRecord : medicalRecords) {

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
		List<PersonModel> persons = new ArrayList<PersonModel>();
		persons = personService.getPersonsByCity(city);
		List<String> CommunityEmail = new ArrayList<String>();
		
		for (PersonModel person : persons) {
			CommunityEmail.add(person.getEmail());
			}
		
		return CommunityEmail;
	}

}
