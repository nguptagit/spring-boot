package userapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import userapi.dto.SuccessResponse;
import userapi.dto.UserDTO;
import userapi.exception.CustomExceptionEnum;
import userapi.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController  {

	@Autowired
	private UserService _userService;
	
	/**
	 * Get user by Email
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/get-by-email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO getByEmail(@PathVariable(value = "email") String email) {
		return _userService.getByEmail(email);
	}
	
	
	/**
	 * Get User by Id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO getById(@PathVariable(value = "id") Long id) {
		return _userService.getById(id);
	}
	
	
	
	
	/**
	 * Update User Profile
	 * @param detail
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse update(@Valid @RequestBody UserDTO detail) {
		_userService.update(detail);
		return new SuccessResponse(CustomExceptionEnum.SUCCESS);

	}

} // class UserController
