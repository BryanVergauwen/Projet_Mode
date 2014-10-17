/**
 * 
 */
package exceptions;

/**
 * @author vergauwb
 *
 */
public class MalFormedFaceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MalFormedFaceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MalFormedFaceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MalFormedFaceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MalFormedFaceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
