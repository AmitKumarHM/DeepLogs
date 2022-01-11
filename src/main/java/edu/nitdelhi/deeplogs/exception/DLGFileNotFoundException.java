package edu.nitdelhi.deeplogs.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class DLGFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DLGFileNotFoundException(String message) {
        super(message);
    }

    public DLGFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
