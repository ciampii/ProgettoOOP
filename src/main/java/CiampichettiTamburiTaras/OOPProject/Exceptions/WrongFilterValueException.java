package CiampichettiTamburiTaras.OOPProject.Exceptions;

public class WrongFilterValueException extends Exception{
String errMsg;
	
	public WrongFilterValueException(String msg) {
		super();
		this.errMsg = msg;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
}
