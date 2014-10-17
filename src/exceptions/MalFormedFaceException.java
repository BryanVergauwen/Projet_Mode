package exceptions;

public class MalFormedFaceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MalFormedFaceException() {
	}

	public MalFormedFaceException(String message) {
		super(message);
	}

	public MalFormedFaceException(Throwable cause) {
		super(cause);
	}

	public MalFormedFaceException(String message, Throwable cause) {
		super(message, cause);
	}
}
