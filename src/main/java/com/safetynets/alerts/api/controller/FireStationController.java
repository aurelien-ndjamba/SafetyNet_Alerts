package com.safetynets.alerts.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynets.alerts.api.model.FireStationDataBaseModel;
import com.safetynets.alerts.api.model.InfoByStationNumber;
import com.safetynets.alerts.api.service.FireStationService;

@RestController
public class FireStationController {

	@Autowired private FireStationService fireStationService;

	/** GET	http://localhost:8080/firestation */
	@GetMapping("/firestation")
	public List<FireStationDataBaseModel> getAllFireStation() {
		return fireStationService.getAllFireStation();
	}
	
	/** GET	http://localhost:8080/firestation?stationNumber=2 */
	@RequestMapping(value = "/firestation", method = RequestMethod.GET, params = { "station" })
	public InfoByStationNumber getInfoByStationNumber(long station) throws ParseException {
		return fireStationService.getInfoByStationNumber(station);
	}

	/** POST	http://localhost:8080/firestation */
	@PostMapping("/firestation")
	@ResponseStatus(HttpStatus.CREATED)
	public FireStationDataBaseModel postFireStation(@RequestBody FireStationDataBaseModel fireStation) {
		return fireStationService.postFireStation(fireStation);
	}

	/** PUT	http://localhost:8080/firestation */
	@PutMapping("/firestation")
	public boolean updateFireStation(@RequestBody FireStationDataBaseModel firestation) {
		return fireStationService.updateFireStation(firestation);
	}

	/** DELETE	http://localhost:8080/firestation?address=951 LoneTree Rd */
	@RequestMapping(value = "/firestation", method = RequestMethod.DELETE, params = { "address" })
	public void deleteFireStationByAddress(
			@RequestParam("address") String address) throws ParseException {
		fireStationService.deleteFireStationByAddress(address);
	}
	
}