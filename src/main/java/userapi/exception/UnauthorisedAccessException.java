package userapi.exception;

/**
 * @author nikhil.gupta
 *
 */
public class UnauthorisedAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private CustomExceptionEnum errorEnum;

	/**
	 * @param key
	 */
	public UnauthorisedAccessException(String key) {
		super(key);
	}

	/**
	 * @param msg
	 * @param code
	 */
	public UnauthorisedAccessException(final String msg, final CustomExceptionEnum code) {
		super(msg);
		this.errorEnum = code;
	}

	/**
	 * @param code
	 */
	public UnauthorisedAccessException(final CustomExceptionEnum code) {
		super();
		this.errorEnum = code;
	}
	
	public CustomExceptionEnum getErrorEnum() {
		return errorEnum;
	}

	
}