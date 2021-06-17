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

import CiampichettiTamburiTaras.OOPProject.Model.Classifica;
import CiampichettiTamburiTaras.OOPProject.Model.Data.*;

public class Stats {

	public int totAreas() {
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

		return areas;
	}

	@SuppressWarnings("unchecked")
	public JSONObject maxMinAveSquads() {
		JSONArray obj = null;
		JSONObject output = new JSONObject();
		String compMax = null;
		String compMin = null;
		long totSquadre = 0;
		long max = -1;
		long min = 50;

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

	@SuppressWarnings("unchecked")
	public JSONObject areaStats() {
		JSONObject workingObj = new CompetitionAPI().getAllCompetitions();

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
	
	public int aveDuration() {
		JSONObject obj = new CompetitionAPI().getAllCompetitions();
		JSONArray competitions = (JSONArray) obj.get("competitions");
		long durataCampionato = 0;
		long totDurate = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 0; i < totAreas(); i++) {
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
		
		int toRet = (int) totDurate / 153;
		return toRet;
	}
}
