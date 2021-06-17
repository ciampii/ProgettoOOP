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


public class ClassificaMarcatoriApi {
	private Vector<String> marcatori = new Vector<String>();
	private Vector<String> squadre = new Vector<String>();
	private Vector<Integer> numeroGol = new Vector<Integer>();

	private String idCompetizione = null;

	public ClassificaMarcatoriApi(String idCompetizione) throws IOException, ParseException {

		this.idCompetizione = idCompetizione;
		try {

			URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ this.idCompetizione+"/scorers"
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

			JSONArray scorers = (JSONArray)obj.get("scorers");
			
			for(int i = 0; i < scorers.size(); i++) {
				
				JSONObject oggettoPlayer=(JSONObject) scorers.get(i);
				
				JSONObject oggettoNome = (JSONObject) oggettoPlayer.get("player");
				String nomeGiocatore = oggettoNome.get("name").toString();
				marcatori.add(nomeGiocatore);
				
				
				JSONObject oggettoSquadra = (JSONObject) oggettoPlayer.get("team");
				String nomeSquadra = oggettoSquadra.get("name").toString();
				squadre.add(nomeSquadra);
				
				numeroGol.add(((Long) oggettoPlayer.get("numberOfGoals")).intValue());	
			
			}


		} catch(Exception e) {
			System.out.print(e);
		}

		for (int i = 0; i < marcatori.size(); i++) {
			System.out.println(i+1 + ") " + marcatori.get(i) + " " + numeroGol.get(i) + "   " + squadre.get(i));
		}
	}

	public String readVectors() {
		String lunga = "";
		for (int i = 0; i < marcatori.size(); i++) {
			lunga.concat(i + ") " + marcatori.get(i) + " " + numeroGol.get(i) + "   " + squadre.get(i));
		}
		return lunga;
	}
}
