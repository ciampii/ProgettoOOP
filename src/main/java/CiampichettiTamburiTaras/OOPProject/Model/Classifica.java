package CiampichettiTamburiTaras.OOPProject.Model;

public class Classifica {
	private String nomeLega;
	private int posizione;
	private String nomeSquadra;
	private int partiteGiocate;
	private int punti;
	private int golFatti;
	private int golSubiti;
	private int differenzaReti;
	private int tipoClassifica;
	
	public Classifica() {
		super();
	}
	
	public Classifica(String nomeLega, int posizione, String nomeSquadra, int partiteGiocate,
			int punti, int golFatti, int golSubiti, int differenzaReti, int tipoClassifica) {
		super();
		this.nomeLega = nomeLega;
	 	this.posizione = posizione;
		this.nomeSquadra = nomeSquadra;
		this.partiteGiocate = partiteGiocate;
		this.punti = punti;
		this.golFatti = golFatti;
		this.golSubiti = golSubiti;
		this.differenzaReti = differenzaReti;
		this.tipoClassifica = tipoClassifica;
	}
	
	public String getNomeLega() {
		return nomeLega;
	}

	public void setNomeLega(String nomeLega) {
		this.nomeLega = nomeLega;
	}

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	public String getNomeSquadra() {
		return nomeSquadra;
	}

	public void setNomeSquadra(String nomeSquadra) {
		this.nomeSquadra = nomeSquadra;
	}

	public int getPartiteGiocate() {
		return partiteGiocate;
	}

	public void setPartiteGiocate(int partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}

	public int getGolFatti() {
		return golFatti;
	}

	public void setGolFatti(int golFatti) {
		this.golFatti = golFatti;
	}

	public int getGolSubiti() {
		return golSubiti;
	}

	public void setGolSubiti(int golSubiti) {
		this.golSubiti = golSubiti;
	}

	public int getDifferenzaReti() {
		return differenzaReti;
	}

	public void setDifferenzaReti(int differenzaReti) {
		this.differenzaReti = differenzaReti;
	}

	public int getTipoClassifica () {
		return tipoClassifica;
	}
	
	public void setTipoClassifica (int tipoClassifica) {
		this.tipoClassifica = tipoClassifica;
	}
}
