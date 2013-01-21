package fi.muni.redhat.hiperex.exception;

public class EmptyIdPoolException extends Exception{

	private String message = "Id pool is empty. Probably, there is not enough entities to be removed.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyIdPoolException(){};

	public EmptyIdPoolException(String message) {
		this.message = message;
	}

	public EmptyIdPoolException(Throwable cause) {
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
