package CiampichettiTamburiTaras.OOPProject.Model;

public class ClassificaMarcatori {
	private String marcatori;
	private String squadre;
	private int numeroGol;
	
	public ClassificaMarcatori( String marcatori,  String squadre,  int numeroGol) {
		super();
		this.marcatori=marcatori;
		this.squadre=squadre;
		this.numeroGol=numeroGol;
	}
	
	public String getMarcatori() {
		return marcatori;
	}

	public String getSquadre() {
		return squadre;
	}

	public void setSquadre(String squadre) {
		this.squadre = squadre;
	}

	public int getNumeroGol() {
		return numeroGol;
	}

	public void setNumeroGol(int numeroGol) {
		this.numeroGol = numeroGol;
	}

	public void setMarcatori(String marcatori) {
		this.marcatori = marcatori;
	}
}
