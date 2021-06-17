package CiampichettiTamburiTaras.OOPProject.Exceptions;

/*
 * Classe che contiene l'eccezione per la mancanza di file Config
 */
public class noConfigFoundException extends Exception{
	
	String errMsg;
	
	/*
	 * Costruttore
	 * @param msg rappresenta il messaggio di errore
	 */
	public noConfigFoundException (String msg) {
		super();
		this.errMsg = msg;
	}
	
	/*
	 * Restituisce il messaggio di errore
	 * @return errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}
}
