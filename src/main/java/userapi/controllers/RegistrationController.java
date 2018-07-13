package userapi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import userapi.dto.SuccessResponse;
import userapi.dto.UserDTO;
import userapi.exception.CustomExceptionEnum;
import userapi.service.UserService;

@RestController
public class RegistrationController {
	
	@Autowired
	private UserService _userService;
	
	
	
	/**
	 * Controller to register a user 
	 * @param detail
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse register(@Valid @RequestBody UserDTO detail,HttpServletRequest request)  {
		StringBuilder appUrl = new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
		_userService.create(detail,appUrl.toString());
		return new SuccessResponse(CustomExceptionEnum.USER_REGIS_SUCCESS);
			
	}
	
	/**
	 * Controller to confirm user via link end to user email while registration
	 * @param detail
	 * @return
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse confirm( @RequestParam("token") String token)  {
		_userService.confirmUserRegByToken(token);
		return new SuccessResponse(CustomExceptionEnum.REG_LINK_VERIFIED);
			
	}
	
	
	

}
