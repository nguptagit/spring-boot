package userapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import userapi.dto.ErrorDetail;
import userapi.exception.CustomExceptionEnum;
import userapi.exception.CustomSystemException;
import userapi.exception.DataNotFoundException;
import userapi.exception.DatabaseException;
import userapi.exception.UnauthorisedAccessException;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

	
	/**
	 * This method will catch all Exception for Unauthorised Access and create Custom message
	 * @param ex
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorisedAccessException.class)
	public ErrorDetail handleError(UnauthorisedAccessException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(ex.getErrorEnum().getExceptionCode());
		error.setReason_desc(ex.getErrorEnum().getExceptionDescription());
		return error;
	}
	
	
	/**
	 * This method will catch all Exception for Data Not Found and create Custom message
	 * @param ex
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(DataNotFoundException.class)
	public ErrorDetail handleError(DataNotFoundException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(ex.getErrorEnum().getExceptionCode());
		error.setReason_desc(ex.getErrorEnum().getExceptionDescription());
		return error;
	}
	
	
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(DatabaseException.class)
	public ErrorDetail handleError(DatabaseException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(ex.getErrorEnum().getExceptionCode());
		error.setReason_desc(ex.getErrorEnum().getExceptionDescription());
		return error;
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(CustomSystemException.class)
	public ErrorDetail handleError(CustomSystemException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(ex.getErrorEnum().getExceptionCode());
		error.setReason_desc(ex.getErrorEnum().getExceptionDescription());
		return error;
	}
	

	
	
	/**
	 * This method will catch all TypeMismatchException for a Controller  and create Custom message
	 * @param ex
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TypeMismatchException.class)
	public ErrorDetail handleError(TypeMismatchException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(CustomExceptionEnum.INVALID_REQUEST.getExceptionCode());
		error.setReason_desc(CustomExceptionEnum.INVALID_REQUEST.getExceptionDescription());
		return error;
	}
	
	
	
	
	/**
	 * This Method will catch Invalid input Exceptions
	 * @param ex
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDetail handleError(MethodArgumentNotValidException ex) {
		ErrorDetail error = new ErrorDetail();
		error.setReason_code(CustomExceptionEnum.INVALID_REQUEST_PARAM.getExceptionCode());
		error.setReason_desc(CustomExceptionEnum.INVALID_REQUEST_PARAM.getExceptionDescription());
		if (ex.getBindingResult().hasErrors()) {
			List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
			Map<String, String> fieldMap = new HashMap<>(fieldErrors.size());
			for (FieldError fieldError : fieldErrors) {
				if (fieldMap.containsKey(fieldError.getField())) {
					StringBuilder builder = new StringBuilder(fieldMap.get(fieldError.getField()));
					builder.append(", ").append(fieldError.getDefaultMessage());
				} else {
					fieldMap.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
			}
			error.setFields(fieldMap);
		}
		return error;
	}
	
	
}
