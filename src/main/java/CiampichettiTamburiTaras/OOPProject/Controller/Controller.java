package CiampichettiTamburiTaras.OOPProject.Controller;

import CiampichettiTamburiTaras.OOPProject.Model.Data.ApiDate;
import CiampichettiTamburiTaras.OOPProject.Model.Data.ApiMatchday;
import CiampichettiTamburiTaras.OOPProject.Model.Data.ChiamataSquadre;
import CiampichettiTamburiTaras.OOPProject.Model.Data.ClassificaApi;
import CiampichettiTamburiTaras.OOPProject.Model.Data.ClassificaMarcatoriApi;
import CiampichettiTamburiTaras.OOPProject.Utils.Stats;
import CiampichettiTamburiTaras.OOPProject.Utils.writeConfigFile;
import CiampichettiTamburiTaras.OOPProject.*;
import CiampichettiTamburiTaras.OOPProject.Model.*;
import CiampichettiTamburiTaras.OOPProject.Model.Data.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	
	/*
	 * GetMapping che permette di visualizzare le classifiche delle competizioni, gli id possibili sono:
	{SA}, {PPL}, {PL}, {DED},{BL1},{FL1}, {PD},{ELC}, {BSA}.
	il parametro "tc" può avere valore {0,1,2}: con 0 si visualizza la classifica totale, con 1 la classifica dei punti totalizzati in casa 
	e con 2 si visualizza la classifica dei punti totalizzati in trasferta.
	*/
	
	@GetMapping(value = "/classifica")
	public ResponseEntity<Object> getComp(@RequestParam(name = "id", defaultValue = "SA") String idComp, 
			@RequestParam(name = "tc",defaultValue = "0") int tipoClassifica) {

	 	ClassificaApi cls = null;
		try {
			cls = new ClassificaApi(idComp, tipoClassifica);
		} catch (IOException e) {
			// TO/DO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(cls.readVectors(), HttpStatus.OK);
	}
	
	/*
	 * GetMapping che permettere di visualizzare la classifica dei marcatori delle competizioni, si può usare anche l'id {CL}.
	 * */
	
	@GetMapping(value = "/marcatori")
	public String getClassMarcatori(@RequestParam(name = "id", defaultValue = "SA") String idComp) {
		
		ClassificaMarcatoriApi cls = null;
		try {
			cls = new ClassificaMarcatoriApi(idComp);
		} catch (IOException e) {
			// TO/DO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cls.readVectors();
	}

	/*
	 * GetMapping che permette di visualizzare le squadre che compongono le competizioni e il loro id.
	 * */
	
	@GetMapping(value = "/squadre")
	public String getSquadre(@RequestParam(name = "id", defaultValue = "SA") String idComp) {
		
		ChiamataSquadre cls = null;
		
		try {
			cls = new ChiamataSquadre(idComp);
		} catch (IOException e) {
			// TO/DO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cls.readVectors();
	}
	
	/*
	 * GetMapping che permette di visualizzare le partite di una determinata stagione e giornata, l'id {CL} non ha questa estensione.
	 * il parametro "giornata" va dal 1 al 38.
	 * il parametro "stagione" va dal 2018 al 2020.
	 * */

	@GetMapping(value = "/matchday")
	public String getMatchdaye(@RequestParam(name = "id", defaultValue = "SA") String idComp,
			@RequestParam(name = "stagione", defaultValue = "2020") int stagione,
			@RequestParam(name = "giornata", defaultValue = "1") int giornata) {
		
		ApiMatchday cls = null;
		
		try {
			cls = new ApiMatchday(idComp, stagione, giornata);
		} catch (IOException e) {
			// TO/DO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cls.readVectors();
	}

	/*GetMapping che permette di visualizzare le partite di una determinata stagione e a partire da una data ad un'altra.
	 * i parametri "da" e "a" devono essere scritti "YYYY-MM-DD".
	 * */
	
	@GetMapping(value = "/matchdate")
	public ResponseEntity<Object> getMatchdaye(@RequestParam(name = "id", defaultValue = "SA") String idComp,
			@RequestParam(name = "stagione", defaultValue = "2020") int stagione,
			@RequestParam(name = "da", defaultValue = "2020-09-01") String dataDa,
	        @RequestParam(name = "a", defaultValue = "2021-07-01") String dataA) throws IOException, ParseException{
		
		ApiDate cls = new ApiDate(idComp, stagione, dataDa, dataA);
		
		return new ResponseEntity<> (cls.readVectors(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stats")
	public ResponseEntity<Object> getStatsArea() throws MalformedURLException, IOException, ParseException {
		Stats stats = new Stats();
		return new ResponseEntity<> (stats.areaStats(), HttpStatus.OK);
	}
	
	@GetMapping(value="/config1")
	public ResponseEntity<Object> createConfig1() {
		writeConfigFile Conf = new writeConfigFile();
		Conf.writeJSONconfig1();
		return new ResponseEntity<>("File creato!", HttpStatus.OK);
	}
	
	@GetMapping(value="/config2")
	public ResponseEntity<Object> createConfig2() {
		writeConfigFile Conf = new writeConfigFile();
		Conf.writeJSONconfig2();
		return new ResponseEntity<>("File creato!", HttpStatus.OK);
	}
	//@PostMapping(value = "")

}

