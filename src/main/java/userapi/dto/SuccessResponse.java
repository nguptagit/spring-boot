package userapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import userapi.exception.CustomExceptionEnum;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {

	private String status ;
	private String message ;
	
	private String token;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

	public SuccessResponse() {
	}
	

	public SuccessResponse(final String status, final String message) {
		this.status = status;
		this.message = message;
	}
	
	public SuccessResponse(final CustomExceptionEnum customExceptionEnum) {
		this.status = customExceptionEnum.getExceptionCode();
		this.message = customExceptionEnum.getExceptionDescription();
	}
	
	public SuccessResponse(final CustomExceptionEnum customExceptionEnum, final String token) {
		this.status = customExceptionEnum.getExceptionCode();
		this.message = customExceptionEnum.getExceptionDescription();
		this.token = token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	
	
	
}
