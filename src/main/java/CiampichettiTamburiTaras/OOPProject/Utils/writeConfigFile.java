package CiampichettiTamburiTaras.OOPProject.Utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class writeConfigFile {

		private String[] idLegheAmmesse = {"CL", "PL", "SA", "BL1", "ELC", "PD"};
		private String[] idLegheAmmesse2 = {"DED", "FL1", "WC", "EC", "BSA", "PPL"};

		private JSONArray confArray;
		private JSONObject compObj;
		private JSONArray squadsArray;
		private JSONObject squadNameID;
		
		public writeConfigFile() {
			this.confArray = new JSONArray();
			this.compObj = new JSONObject();
			this.squadsArray = new JSONArray();
			this.squadNameID = new JSONObject();
		}
		
		@SuppressWarnings("unchecked")
		public void writeJSONconfig1() {
			confArray = new JSONArray();
			try {
				for (String id: idLegheAmmesse) {
					
					compObj = new JSONObject();
					squadsArray = new JSONArray();
					
					URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ id +"/teams").openConnection();
					openConnection.addRequestProperty("X-Auth-Token", "d83eba6e4c5b47aeab76f99e35c2aef5");
					InputStream in = openConnection.getInputStream();	

					String data = "";
					String line = "";

					try {
						InputStreamReader inR = new InputStreamReader( in );
						BufferedReader buf = new BufferedReader( inR );

						while ((line = buf.readLine()) != null ){
							data += line;
						}
					} finally {
						in.close();
					}


					JSONObject obj = (JSONObject) JSONValue.parseWithException(data);
					
				
					JSONObject compData = (JSONObject) obj.get("competition");
					String comp = compData.get("name").toString();
					String teams = obj.get("count").toString();
					compObj.put("count", teams);
					compObj.put("competition", comp);
					JSONArray squadre = (JSONArray) obj.get("teams");

					for(int i=0; i < squadre.size(); i++) {
						squadNameID = new JSONObject();
						JSONObject oggettoSquadra = (JSONObject) squadre.get(i);
						squadNameID.put("squad", oggettoSquadra.get("name").toString());
						squadNameID.put("id", (Long) oggettoSquadra.get("id"));
						squadsArray.add(squadNameID);
					}
					compObj.put("teams", squadsArray);
					confArray.add(compObj);
				}
			}
			catch(Exception e) {
				System.out.print(e);
			}
			
			try (FileWriter file = new FileWriter("config.json")) {
	            file.write(confArray.toJSONString()); 
	            file.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		@SuppressWarnings("unchecked")
		public void writeJSONconfig2() {
			confArray = new JSONArray();
			try {
				for (String id: idLegheAmmesse2) {
					
					compObj = new JSONObject();
					squadsArray = new JSONArray();
					
					URLConnection openConnection = new URL("https://api.football-data.org/v2/competitions/"+ id +"/teams").openConnection();
					openConnection.addRequestProperty("X-Auth-Token", "d83eba6e4c5b47aeab76f99e35c2aef5");
					InputStream in = openConnection.getInputStream();	

					String data = "";
					String line = "";

					try {
						InputStreamReader inR = new InputStreamReader( in );
						BufferedReader buf = new BufferedReader( inR );

						while ((line = buf.readLine()) != null ){
							data += line;
						}
					} finally {
						in.close();
					}


					JSONObject obj = (JSONObject) JSONValue.parseWithException(data);
					
				
					JSONObject compData = (JSONObject) obj.get("competition");
					String comp = compData.get("name").toString();
					String teams = obj.get("count").toString();
					compObj.put("count", teams);
					compObj.put("competition", comp);
					JSONArray squadre = (JSONArray) obj.get("teams");

					for(int i=0; i < squadre.size(); i++) {
						squadNameID = new JSONObject();
						JSONObject oggettoSquadra = (JSONObject) squadre.get(i);
						squadNameID.put("squad", oggettoSquadra.get("name").toString());
						squadNameID.put("id", (Long) oggettoSquadra.get("id"));
						squadsArray.add(squadNameID);
					}
					compObj.put("teams", squadsArray);
					confArray.add(compObj);
				}
			}
			catch(Exception e) {
				System.out.print(e);
			}
			
			try (FileWriter file = new FileWriter("config2.json")) {
	            file.write(confArray.toJSONString());
	            file.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
