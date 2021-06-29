package com.safetynets.alerts.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynets.alerts.api.model.ChildInfoModel;
import com.safetynets.alerts.api.model.FireStationModel;
import com.safetynets.alerts.api.model.PersonsByAddress;
import com.safetynets.alerts.api.model.MedicalRecordModel;
import com.safetynets.alerts.api.model.PersonModel;
import com.safetynets.alerts.api.model.PersonInfoGlobalModel;
import com.safetynets.alerts.api.model.PersonInfoAdvancedModel;

/** 
 * Classe qui s'occupe des services commun de l'applicaiton
 * 
 * @author dworkin
 * @version 1.0
 */
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

	/** 
	 * liste les enfants vivants à l'addresse en parametre sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	int age
	 * -	HashSet<PersonModel> familyRelationShip -> Liste des autres membres du foyer (majeur et mineur compris)
	 * 
	 * @param	address(String)
	 * @return	List<ChildInfoModel>
	 * 
	 */
	public List<ChildInfoModel> getChildInfos(String address) throws ParseException {

		List<ChildInfoModel> childInfos = new ArrayList<ChildInfoModel>();
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

				childInfos.add(childInfo);
			}

		}
		return childInfos;
	}

	/** 
	 * Liste les numéros de téléphones des résidents désservis par la caserne de pompier
	 * Les doublons ne sont pas pris en compte.
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced(HashSet<String>)
	 * 
	 */
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

	/** 
	 * Liste les informations avancées sur des personnes vivant à l'adresse en paramètre sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	String address
	 * -	Long stationNumber
	 * -	int age
	 * - 	String phone;
	 * -	HashSet<String> medications  -> Liste uniquement les medicaments de cette personne
	 * - 	HashSet<String> allergies -> Liste uniquement les allergies de cette personne
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced
	 * 
	 */
	public ArrayList<PersonInfoAdvancedModel> getPersonsInfoAdvanced(String address) throws ParseException {

		ArrayList<PersonInfoAdvancedModel> personsInfoAdvanced = new ArrayList<PersonInfoAdvancedModel>();
		List<PersonModel> persons = new ArrayList<PersonModel>();
		persons = personService.getPersonsByAddress(address);

		for (PersonModel person : persons) {

			PersonInfoAdvancedModel personInfoAdvanced = new PersonInfoAdvancedModel();
			personInfoAdvanced.setId(person.getId());
			personInfoAdvanced.setFirstName(person.getFirstName());
			personInfoAdvanced.setLastName(person.getLastName());
			personInfoAdvanced.setAddress(person.getAddress());
			personInfoAdvanced.setStationNumber(fireStationService.getStationNumberByAddress(address));
			personInfoAdvanced.setAge(countService.getAge(person.getFirstName(), person.getLastName()));
			personInfoAdvanced.setPhone(person.getPhone());

			MedicalRecordModel medicalRecord = new MedicalRecordModel();
			medicalRecord = medicalRecordService.getMedicalRecordByFirstNameAndLastName(person.getFirstName(),
					person.getLastName());
			personInfoAdvanced.setMedications(medicalRecord.getMedications());
			personInfoAdvanced.setAllergies(medicalRecord.getAllergies());

			personsInfoAdvanced.add(personInfoAdvanced);
		}
		return personsInfoAdvanced;

	}

	/** 
	 * Liste des personnes par adresse à partir des numéros de station en paramètre sous la forme:
	 * 
	 * -	String address
	 * -	List<PersonModel> PersonsByAddress
	 * 
	 * 
	 * @param	address(String)
	 * @return	personsInfoAdvanced
	 * @see PersonService.personsByAddress
	 */
	public List<PersonsByAddress> getPersonsByStations(List<Long> stations) throws ParseException {

		List<PersonsByAddress> listPersonsByAddress = new ArrayList<PersonsByAddress>();
		Iterable<FireStationModel> fireStationsByManyStations = new ArrayList<FireStationModel>();
		fireStationsByManyStations = fireStationService.getFirestationsByManyStation(stations);
		for (FireStationModel fireStation : fireStationsByManyStations) {

			PersonsByAddress personsByStationNumber = new PersonsByAddress();
			personsByStationNumber.setAddress(fireStation.getAddress());
			personsByStationNumber.setPersonsByAddress(personService.getPersonsByAddress(fireStation.getAddress()));

			listPersonsByAddress.add(personsByStationNumber);
		}
		
		return listPersonsByAddress;
	}

	/** 
	 * Liste les informations globales d'une personne sous la forme:
	 * 
	 * -    String firstName
	 * -	String lastName
	 * -	String address
	 * -	int age
	 * -	String email
	 * -	HashSet<String> medications  -> Liste toutes les medicaments de la famille
	 * - 	HashSet<String> allergies -> Liste toutes les allergies de la famille
	 * 
	 * @param	firstName(String)
	 * @param	lastName(String)
	 * @return	personInfoGlobal(PersonInfoGlobalModel)
	 * 
	 */
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

	/** 
	 * Liste les adresses emails des habitants de la ville en paramètre.
	 * Les doublons sont pris en compte
	 * 
	 * @param	city(String)
	 * @return	List<String>
	 * 
	 */
	public List<String> getCommunityEmail(String city) {
		List<PersonModel> persons = new ArrayList<PersonModel>();
		persons = personService.getPersonsByCity(city);
		List<String> communityEmail = new ArrayList<String>();
		
		for (PersonModel person : persons) {
			communityEmail.add(person.getEmail());
			}
		
		return communityEmail;
	}

}
