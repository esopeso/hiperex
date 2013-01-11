package fi.muni.redhat.hiperex.exception;

public class NotUniqueIdException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message = "Duplicit id value detected. Id should be unique!";
	
	public NotUniqueIdException(String message) {
		super(message);
		this.message = message;
	}
	
	public NotUniqueIdException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
