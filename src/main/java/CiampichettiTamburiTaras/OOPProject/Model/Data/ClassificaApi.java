package CiampichettiTamburiTaras.OOPProject.Model.Data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import CiampichettiTamburiTaras.OOPProject.Model.Classifica;

public class ClassificaApi {

	private String nomeLega;
	private Vector<Classifica> classificaFromAPI =  new Vector<Classifica>();

	public ClassificaApi(String idCompetizione, int tipoClassifica) throws IOException, ParseException {

		try {
			URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ idCompetizione +"/standings"
					).openConnection();
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

			//season contiene l'array composoto dai tre array contenenti i dati delle partite totali, fuori e in casa
			JSONArray season = (JSONArray) obj.get("standings");
			JSONObject league = (JSONObject) obj.get("competition");
			//standings contiene i dati delle partite totali
			JSONObject standings = (JSONObject) season.get(tipoClassifica);
			//squads Ã¨ l'array di tutte le squadre, da scorrere nel for
			JSONArray squads = (JSONArray) standings.get("table");

			Classifica squadra;

			for(int i = 0; i < squads.size(); i++) {
				
				squadra = new Classifica();

				JSONObject oggettoTable = (JSONObject) squads.get(i);
				JSONObject oggettoTeam = (JSONObject) oggettoTable.get("team");
				String nomeSquad = oggettoTeam.get("name").toString();

				squadra.setPosizione(((Long)oggettoTable.get("position")).intValue());
				squadra.setNomeSquadra(nomeSquad);
				squadra.setPartiteGiocate(((Long) oggettoTable.get("playedGames")).intValue());
				squadra.setPunti(((Long) oggettoTable.get("points")).intValue());
				squadra.setGolFatti(((Long) oggettoTable.get("goalsFor")).intValue());
				squadra.setGolSubiti(((Long) oggettoTable.get("goalsAgainst")).intValue());
				squadra.setDifferenzaReti(((Long) oggettoTable.get("goalDifference")).intValue());
				classificaFromAPI.add(squadra);
			}

		} catch(Exception e) {
			System.out.print(e);
		}
	}


	public String readVectors() {
		String lunga = "";
		for (int i = 0; i < posizione.size(); i++) {
			lunga.concat(nomeSquadra.get(i) + " " + posizione.get(i) + " " + punti.get(i));
			lunga.concat(partiteGiocate.get(i) + " " + golFatti.get(i) + " " + golSubiti.get(i) + " " + differenzaReti.get(i));
		}
		return lunga;
	}

}
