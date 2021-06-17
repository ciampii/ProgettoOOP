package CiampichettiTamburiTaras.OOPProject.Model.Data;

import java.io.BufferedReader;
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

public class CompetitionAPI {
	
	public CompetitionAPI () {
		getAllCompetitions();
	}

	public JSONObject getAllCompetitions() {
		JSONObject obj = null;

		try {

			URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/").openConnection();
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

			obj = (JSONObject) JSONValue.parseWithException(data);
		}
		catch(Exception e) {
			System.out.print(e);
		}
		
		return obj;
	}
	
	public int getCompetitionDuration () {
		
		
		return (int) durataCampionato;
	}
}
