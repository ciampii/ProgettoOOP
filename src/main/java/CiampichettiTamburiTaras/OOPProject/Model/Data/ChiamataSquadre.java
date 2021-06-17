package CiampichettiTamburiTaras.OOPProject.Model.Data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
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

public class ChiamataSquadre {

	private Vector<Integer> idSquadra = new Vector<Integer>();
	private Vector<String> nomecortoSquadra = new Vector<String>();
	private int numSquadre = 0;
	
	public int getNumSquadre() {
		return numSquadre;
	}

	private String idCompetizione=null;
	
	public ChiamataSquadre(String idCompetizione) throws  IOException, ParseException {
		
		this.idCompetizione = idCompetizione;
		
		try {
		
		URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ this.idCompetizione+"/teams").openConnection();
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
		
		numSquadre = (Integer) obj.get("count");
		JSONArray squadre = (JSONArray) obj.get("teams");
		
		for(int i=0; i<squadre.size(); i++) {
			
			JSONObject oggettoSquadra=(JSONObject) squadre.get(i);
			idSquadra.add(((Long) oggettoSquadra.get("id")).intValue());
			
			String nomeSquadra = oggettoSquadra.get("shortName").toString();
			nomecortoSquadra.add(nomeSquadra);
			
		}
	}
		catch(Exception e) {
			System.out.print(e);
		}
		for (int i = 0; i < idSquadra.size(); i++) {
			System.out.println( nomecortoSquadra.get(i) + " " + idSquadra.get(i) );
		}
 }

	public String readVectors() {
		String lunga ="";
		for (int i = 0; i < idSquadra.size(); i++) {
			lunga.concat( idSquadra.get(i) + " " + nomecortoSquadra.get(i) );
		}
		return lunga;
	}
	
}
