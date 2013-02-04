package fi.muni.redhat.hiperex.exception;

public class SuspiciousTimeExcaption extends Exception{
	private static final long serialVersionUID = 1L;

	private String message = "Suspicious execution time. Execution time shouldn't be 0";

	public SuspiciousTimeExcaption(String message) {
		super(message);
		this.message = message;
	}

	public SuspiciousTimeExcaption(Throwable cause) {
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
