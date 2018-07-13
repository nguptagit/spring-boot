package userapi.exception;



public enum CustomExceptionEnum {
	
	INVALID_REQUEST("400-001", "Invalid Request."),
	INVALID_REQUEST_PARAM("422-2", "Invalid Request Param."),
	DUPLICATE_EMAIL("422-1", "Email Already in use."),
	VALIDATION_FAILURE("400-999", "Validation Failure"),
	DATA_NOT_FOUND("404-1","Data not found"),
	USER_NOT_EXIST("404-2","User not exist"),
	REG_LINK_EXPIRED("422-2","Link already expired"),
	USER_REGIS_SUCCESS("1","User Successfully registered. For login, please verify your email by clicking the link send to your registerd Email."),
	FORGOT_PASS_SUCCESS("1","Please update your password by clicking the link sent to your registerd Email ."),
	REG_LINK_VERIFIED("1","Account verified. Please login.."),
	PASSWORD_LINK_VERIFIED("1","Link verified. Please update your password."),
	PASSWORD_UPDATE("1","Password updated."),
	SUCCESS("1","SUCCESS"),
	INVALID_CREDENTIAL("401-1","Invalid username or password"),
	INVALID_TOKEN("401-2","Invalid token"),
	INTERNAL_SYSTEM_EXCEPTION("900-001", "Internal system exception");
	
	
	private String exceptionCode;
	private String exceptionDescription;
	
	
	private CustomExceptionEnum(String exceptionCode, String exceptionDescription){
		this.setExceptionCode(exceptionCode);
		this.setExceptionDescription(exceptionDescription);
	}


	public String getExceptionDescription() {
		return exceptionDescription;
	}


	public void setExceptionDescription(String exceptionDescription) {
		this.exceptionDescription = exceptionDescription;
	}
	

	public String getExceptionCode() {
		return exceptionCode;
	}


	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
