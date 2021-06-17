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

public class ApiRosa {

	private Vector<String> nome = new Vector<String>();
	private Vector<String> nazionalità = new Vector<String>();
	private Vector<String> ruolo = new Vector<String>();


	private int idSquadra=0;

	public ApiRosa(int idSquadra) throws  IOException, ParseException {

		this.idSquadra = idSquadra;

		try {

			URLConnection openConnection = new URL("https://api.football-data.org/v2/teams/"+ this.idSquadra).openConnection();
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

			JSONArray squadra = (JSONArray) obj.get("squad");

			for(int i=0; i<squadra.size(); i++) {

				JSONObject oggettoSquadra=(JSONObject) squadra.get(i);

				String nomecognome = oggettoSquadra.get("name").toString();
				nome.add(nomecognome);

				String nationality = oggettoSquadra.get("nationality").toString();
				nazionalità.add(nationality);

				String role = oggettoSquadra.get("role").toString();
				ruolo.add(role);

			}

		}catch(Exception e) {
			System.out.print(e);
		}	
	}


	public String readVectors() {
		String lunga ="";
		for (int i = 0; i < nome.size(); i++) {
			lunga.concat(nome.get(i) + " " + ruolo.get(i) + " " + nazionalità.get(i) );
		}
		return lunga;
	}
}