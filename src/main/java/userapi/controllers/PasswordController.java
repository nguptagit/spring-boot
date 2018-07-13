package userapi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import userapi.dto.ForgotPasswordDTO;
import userapi.dto.SuccessResponse;
import userapi.dto.UpdatePasswordDTO;
import userapi.exception.CustomExceptionEnum;
import userapi.service.UserService;

@RestController
@RequestMapping (value = "/password")
public class PasswordController {

	
	@Autowired
	private UserService _userService;
	
	
	
	/**
	 * Controller to send the forgot password link to user email
	 * @param forgot
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgot,HttpServletRequest request)  {
		StringBuilder appUrl = new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
		_userService.sendForgotPasswordLink(forgot,appUrl.toString());
		return new SuccessResponse(CustomExceptionEnum.FORGOT_PASS_SUCCESS);
			
	}
	
	
	/**
	 * Controller to verify email link sent for forgot password
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/verifyToken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse verifyToken( @RequestParam("token") String token)  {
		_userService.confirmUserByForgotPassToken(token);
		return new SuccessResponse(CustomExceptionEnum.PASSWORD_LINK_VERIFIED,token);
			
	}
	
	

	/**
	 * Controller to update forgot password
	 * @param forgot
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse updateForgotPassword(@Valid @RequestBody UpdatePasswordDTO uPasswordDTO)  {
		_userService.updateForgotPassword(uPasswordDTO);
		return new SuccessResponse(CustomExceptionEnum.PASSWORD_UPDATE);
			
	}
	
	
	/**
	 * Controller to change password
	 * @param forgot
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse changePassword(@Valid @RequestBody UpdatePasswordDTO uPasswordDTO,Authentication authentication)  {
		_userService.changePassword (authentication.getPrincipal().toString(),uPasswordDTO);
		return new SuccessResponse(CustomExceptionEnum.PASSWORD_UPDATE);
			
	}
	
	
	
}
