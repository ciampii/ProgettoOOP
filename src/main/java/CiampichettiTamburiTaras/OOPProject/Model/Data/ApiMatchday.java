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


public class ApiMatchday {

	private Vector<Integer> punteggioCasa = new Vector<Integer>();
	private Vector<Integer> punteggioOspite = new Vector<Integer>();
	private Vector<String> squadraCasa= new Vector<String>();
	private Vector<String> squadraOspite= new Vector<String>();
	
	private String idCompetizione=null;
	private int season=0;
	private int matchday=0;
	
	public ApiMatchday(String idCompetizione, int season, int matchday) throws  IOException, ParseException {
		
		this.idCompetizione=idCompetizione;
		this.season=season;
		this.matchday=matchday;
		
		try {
			
			URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ this.idCompetizione+"/matches?season="+this.season+"&matchday="+this.matchday).openConnection();
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
			
			JSONArray partite =(JSONArray) obj.get("matches");
			
			for(int i=0; i<partite.size(); i++) {
				
				JSONObject partitaSingola=(JSONObject) partite.get(i);
			
				JSONObject oggettoPunteggio=(JSONObject) partitaSingola.get("score");
				JSONObject punteggioFullTime=(JSONObject) oggettoPunteggio.get("fullTime");
				punteggioCasa.add(((Long) punteggioFullTime.get("homeTeam")).intValue());
				punteggioOspite.add(((Long) punteggioFullTime.get("awayTeam")).intValue());
				
				JSONObject oggettoCasa=(JSONObject) partitaSingola.get("homeTeam");
				String squadraNomeCasa = oggettoCasa.get("name").toString();
				squadraCasa.add(squadraNomeCasa);
				
				JSONObject oggettoOspite=(JSONObject) partitaSingola.get("awayTeam");
				String squadraNomeOspite = oggettoOspite.get("name").toString();
				squadraOspite.add(squadraNomeOspite);
				
			}
			
		}	catch(Exception e) {
			System.out.print(e);
		}
		for (int i = 0; i < punteggioCasa.size(); i++) {
			System.out.println( squadraCasa.get(i) + "-" + squadraOspite.get(i) +" "+ punteggioCasa.get(i)+"-"+punteggioOspite.get(i));
		}
	}
	
	public String readVectors() {
		String lunga ="";
		for (int i = 0; i < punteggioCasa.size(); i++) {
			lunga.concat(punteggioCasa.get(i) + " " + punteggioOspite.get(i)+" "+  punteggioCasa.get(i)+"-"+punteggioOspite.get(i) );
		}
		return lunga;
}
}
