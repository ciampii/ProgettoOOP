package CiampichettiTamburiTaras.OOPProject.Exceptions;

public class WrongFilterException extends Exception{
	
	String errMsg;
	
	public WrongFilterException(String msg) {
		super();
		this.errMsg = msg;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
}
