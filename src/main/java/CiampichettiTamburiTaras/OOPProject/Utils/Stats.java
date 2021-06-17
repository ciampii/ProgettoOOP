package CiampichettiTamburiTaras.OOPProject.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import CiampichettiTamburiTaras.OOPProject.Exceptions.noConfigFoundException;
import CiampichettiTamburiTaras.OOPProject.Model.Classifica;
import CiampichettiTamburiTaras.OOPProject.Model.Data.*;

/*
 * *Classe che fornisce le statistiche generali prese dalle API utilizzando dei metodi ausiliari in essa contenuti
 * Le statistiche che fornisce sono: competizione con più squadre, competizione con meno squadre, numero minimo di squadre, numero massimo di squadre,
 * durata media delle competizioni in giorni e media di stagioni salvate.
 */
public class Stats {

	private int totAreas;
	/*
	 * Metodo che prende il totale delle aree e lo ritorna per essere utilizzato
	 * @return int con il totale delle aree
	 */
	public void totAreas() {
		int areas = 0;
		try {
			URLConnection openConnection = new URL("https://api.football-data.org/v2/areas").openConnection();
			openConnection.addRequestProperty("X-Auth-Token", "d83eba6e4c5b47aeab76f99e35c2aef5");
			InputStream in = openConnection.getInputStream();	

			String data = "";
			String line = "";

			try {
				InputStreamReader inR = new InputStreamReader( in );
				BufferedReader buf = new BufferedReader( inR );

				while ( ( line = buf.readLine() ) != null ) {
					data+= line;
				}
			} finally {
				in.close();
			}

			JSONObject obj = (JSONObject) JSONValue.parseWithException(data);

			areas = ((Long)obj.get("count")).intValue();
		} catch(Exception e) {
			System.out.print(e);
		}

		totAreas = areas;
	}

	/*
	 * Metodo che leggendo i file di config contenuti nella directory del progetto, che contengono i dati delle competizioni a cui e'
	 * possibile accedere con il piano base delle API, calcola numero massimo, minimo e medio delle squadre che vi partecipano
	 * 
	 * @return JSONObject contenente i dati richiesti
	 */
	@SuppressWarnings("unchecked")
	public JSONObject maxMinAveSquads() throws noConfigFoundException {
		JSONArray obj = null;
		JSONObject output = new JSONObject();
		String compMax = null;
		String compMin = null;
		long totSquadre = 0;
		long max = -1;
		long min = 50;
		
		File conf1 = new File("config.json");
		File conf2 = new File("config2.json");
		
		if (!conf1.exists() || !conf2.exists())
			throw new noConfigFoundException("UNO DEI DUE FILE CONFIG NON E' PRESENTE!");

		try {
			String data = "";
			String line = "";
			try {
				BufferedReader buf = new BufferedReader(new FileReader("config.json"));
				while ((line = buf.readLine() ) != null ) {
					data += line;
				}
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			obj = (JSONArray) JSONValue.parseWithException(data);
			
			int numFromFile;
			
			for (int i = 0; i < obj.size(); i++) {
				JSONObject camp = (JSONObject) obj.get(i);
				//JSONObject count = (JSONObject) camp.get("count");
				String strValue = (String) camp.get("count");
				numFromFile = Integer.valueOf(strValue);
				
				totSquadre += numFromFile;
				
				if (numFromFile > max) {
					max = numFromFile;
					compMax = camp.get("competition").toString();
				}
				
				if (numFromFile < min) {
					min = numFromFile;
					compMin = camp.get("competition").toString();
				}
			}
			
		}
		catch (Exception e) {
			System.out.print(e);
		}
		
		try {
			String data = "";
			String line = "";
			try {
				BufferedReader buf = new BufferedReader(new FileReader("config.json"));
				while ((line = buf.readLine() ) != null ) {
					data += line;
				}
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			obj = (JSONArray) JSONValue.parseWithException(data);
			
			int numFromFile;
			
			for (int i = 0; i < obj.size(); i++) {
				JSONObject camp = (JSONObject) obj.get(i);
				//JSONObject count = (JSONObject) camp.get("count");
				String strValue = (String) camp.get("count");
				numFromFile = Integer.valueOf(strValue);
				
				totSquadre += numFromFile;
				
				if (numFromFile > max) {
					max = numFromFile;
					compMax = camp.get("competition").toString();
				}
				
				if (numFromFile < min) {
					min = numFromFile;
					compMin = camp.get("competition").toString();
				}
			}
		}
		catch (Exception e) {
			System.out.print(e);
		}
		
		double average = (double) totSquadre / 12;
		average = Math.round(average);
		
		output.put("MAXComp", compMax);
		output.put("MAXVal", max);
		output.put("minComp", compMin);
		output.put("minVal", min);
		output.put("aveVal", average);
		
		return output;
	}

	/*
	 * Metodo che aggrega tutte le stats per le aree calcolate con i metodi precedenti
	 * 
	 * @return JSONObject contenente i dati del JSONObject di maxMinAveSquads aggiunti a quelli di aveDuration
	 */
	@SuppressWarnings("unchecked")
	public JSONObject areaStats() throws noConfigFoundException {
		CompetitionAPI n = new CompetitionAPI();
		JSONObject workingObj = n.getAllCompetitions();

		JSONArray comp = (JSONArray) workingObj.get("competitions");
		long totComp = (Long) workingObj.get("count");
		JSONObject champ;

		long totSeasonsAvailable = 0;

		for (int i = 0; i < comp.size(); i++) {
			champ = (JSONObject) comp.get(i);
			long availableSeasons = (Long) champ.get("numberOfAvailableSeasons");
			totSeasonsAvailable += availableSeasons;
		}

		double aveSeasonsAvail = (double) totSeasonsAvailable / totComp;
		aveSeasonsAvail = Math.round(aveSeasonsAvail);
		
		JSONObject toRet = maxMinAveSquads();
		toRet.put("averageSeasons", aveSeasonsAvail);
		toRet.put("averageDuration", aveDuration());
		
		return toRet;
	}
	
	/*
	 * Metodo che calcola la media della durata delle competizioni usando i metodi della classe SimpleDateFormat e Date
	 * 
	 * @return int con la media della durata per le competizioni
	 */
	public int aveDuration() {
		JSONObject obj = new CompetitionAPI().getAllCompetitions();
		JSONArray competitions = (JSONArray) obj.get("competitions");
		int count = ((Long) obj.get("count")).intValue();
		long durataCampionato = 0;
		long totDurate = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 0; i < count; i++) {
				JSONObject singleComp = (JSONObject) competitions.get(i);
				JSONObject curr = (JSONObject) singleComp.get("currentSeason");
				Date startDate = dateFormat.parse(curr.get("startDate").toString());
				Date endDate = dateFormat.parse(curr.get("endDate").toString());
			
				durataCampionato = (endDate.getTime() - startDate.getTime())/(1000 * 60 * 60 * 24);
				durataCampionato = Math.round(durataCampionato);
				totDurate += durataCampionato;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		totAreas();
		int appoggio = totAreas;
		int toRet = (int) totDurate / count;
		return toRet;
	}
	
	public String getMAXorMinSquads(String filterType) throws noConfigFoundException {
		JSONObject mMAobj = maxMinAveSquads();
		
		if (filterType.equals("MAX")) {
			String toRet = mMAobj.get("MAXComp").toString() + mMAobj.get("MAXVal").toString();
			return toRet;
		}
		
		if (filterType.equals("min")) {
			String toRet = mMAobj.get("minComp").toString() + mMAobj.get("minVal").toString();
			return toRet;
		}
		
		return "-1";
	}
	
	/*
	public JSONObject MAXOrMinForArea(String filterType) throws noConfigFoundException {
		JSONArray obj = null;
		JSONObject output = new JSONObject();
		String compMax = null;
		String compMin = null;
		long totSquadre = 0;
		long max = -1;
		long min = 50;
		
		File conf1 = new File("config.json");
		File conf2 = new File("config2.json");
		
		String appoggio = null;
		
		if (!conf1.exists() || !conf2.exists())
			throw new noConfigFoundException("UNO DEI DUE FILE CONFIG NON E' PRESENTE!");
		
		try {
			String data = "";
			String line = "";
			try {
				BufferedReader buf = new BufferedReader(new FileReader("config.json"));
				while ((line = buf.readLine() ) != null ) {
					data += line;
				}
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			obj = (JSONArray) JSONValue.parseWithException(data);
			
			int numFromFile;
			
			for (int i = 0; i < obj.size(); i++) {
				JSONObject camp = (JSONObject) obj.get(i);
				String strValue = (String) camp.get("count");
				numFromFile = Integer.valueOf(strValue);
				
				if (camp.get("area").toString() == appoggio)
					totSquadre += numFromFile;
				
				if (numFromFile > max) {
					max = numFromFile;
					compMax = camp.get("competition").toString();
				}
				
				if (numFromFile < min) {
					min = numFromFile;
					compMin = camp.get("competition").toString();
				}
			}
			
		}
		catch (Exception e) {
			System.out.print(e);
		}
		
		try {
			String data = "";
			String line = "";
			try {
				BufferedReader buf = new BufferedReader(new FileReader("config.json"));
				while ((line = buf.readLine() ) != null ) {
					data += line;
				}
				buf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			obj = (JSONArray) JSONValue.parseWithException(data);
			
			int numFromFile;
			
			for (int i = 0; i < obj.size(); i++) {
				JSONObject camp = (JSONObject) obj.get(i);
				String strValue = (String) camp.get("count");
				numFromFile = Integer.valueOf(strValue);
				
				if (camp.get("area").toString() == appoggio)
					totSquadre += numFromFile;
				
				if (numFromFile > max) {
					max = numFromFile;
					compMax = camp.get("competition").toString();
				}
				
				if (numFromFile < min) {
					min = numFromFile;
					compMin = camp.get("competition").toString();
				}
			}
		}
		catch (Exception e) {
			System.out.print(e);
		}
	}
	*/
}
