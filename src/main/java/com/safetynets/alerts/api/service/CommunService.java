package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.MedicalRecordsModel;
import com.safetynets.alerts.api.model.PersonInfoModel;
import com.safetynets.alerts.api.model.PersonsModel;
import com.safetynets.alerts.api.repository.FirestationsRepository;
import com.safetynets.alerts.api.repository.MedicalrecordsRepository;
import com.safetynets.alerts.api.repository.PersonsRepository;

import lombok.Data;

@Data
@Service
public class CommunService {

	@Autowired
	private PersonsRepository personsRepository;
	@Autowired
	private FirestationsRepository firestationsRepository;
	@Autowired
	private MedicalrecordsRepository medicalrecordsRepository;
	@Autowired
	private PersonsModel person;
	@Autowired
	private MedicalRecordsModel medicalRecords;
	@Autowired
	private PersonsService personsService;
	@Autowired
	private PersonInfoModel personInfo;
	@Autowired
	private MedicationsHistoryBylastNameService medicalHistoryMedicationsService;
	@Autowired
	private AllergiesHistoryBylastNameService allergiesHistoryBylastNameService;
	@Autowired
	private AgeService ageService;

	public Iterable<String> getFireStationWhenStationNumberGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getChildrenListWithAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getPhoneListWhenStationNumberGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getPeopleListAndFirestationWhenAddressGiven(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<String> getResidentListAndStationNumberWhenAddressGiven(int station_number) {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// ----------------------------------------------------------------------------------------

	public PersonInfoModel getPersonInfo(String firstName, String lastName) throws ParseException {

		ArrayList<ArrayList<String>> listMedicationsHistory = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> listAllergiesHistory = new ArrayList<ArrayList<String>>();
		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();

		for (Long i : listIdsEntitiesPerson) {

			person = personsRepository.getById(i);

			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

				personInfo.setId(i);
				personInfo.setFirstName(firstName);
				personInfo.setLastName(lastName);
				personInfo.setAddress(person.getAddress());
				int age = ageService.getAge(firstName, lastName);
				personInfo.setAge(age);
				personInfo.setEmail(person.getEmail());
				listMedicationsHistory = medicalHistoryMedicationsService.getMedicalHistory(lastName);
				personInfo.setMedications(listMedicationsHistory);
				listAllergiesHistory = allergiesHistoryBylastNameService.getMedicalHistory(lastName);
				personInfo.setAllergies(listAllergiesHistory);
			}
		}

		return personInfo;
	}

	// ----------------------------------------------------------------------------------------
	// http://localhost:8080/communityEmail?city=<city>
	// ----------------------------------------------------------------------------------------

	public List<String> getCommunityEmail(String city) {

		List<Long> listIdsEntitiesPerson = personsService.getlistIdsEntitiesPerson();
		List<String> listCommunityEmail = new ArrayList<String>();

		for (Long i : listIdsEntitiesPerson) {
			person = personsRepository.getById(i);
			if (person.getCity().equals(city)) {
				listCommunityEmail.add(person.getEmail());
			}
		}

		return listCommunityEmail;
	}

}
