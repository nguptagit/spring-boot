package userapi.exception;

/**
 * @author nikhil.gupta
 *
 */
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private CustomExceptionEnum errorEnum;

	/**
	 * @param key
	 */
	public DataNotFoundException(String key) {
		super(key);
	}

	/**
	 * @param msg
	 * @param code
	 */
	public DataNotFoundException(final String msg, final CustomExceptionEnum code) {
		super(msg);
		this.errorEnum = code;
	}

	/**
	 * @param code
	 */
	public DataNotFoundException(final CustomExceptionEnum code) {
		super();
		this.errorEnum = code;
	}
	
	public CustomExceptionEnum getErrorEnum() {
		return errorEnum;
	}

	
}