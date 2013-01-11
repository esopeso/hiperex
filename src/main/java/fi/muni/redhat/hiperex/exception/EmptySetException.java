package fi.muni.redhat.hiperex.exception;

public class EmptySetException extends Exception {

	private String message = "Query returned empty set. Invalid id provided.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptySetException(){};

	public EmptySetException(String message) {
		this.message = message;
	}

	public EmptySetException(Throwable cause) {
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
