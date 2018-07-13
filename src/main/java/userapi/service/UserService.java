package userapi.service;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import userapi.dao.UserDao;
import userapi.dto.ForgotPasswordDTO;
import userapi.dto.UpdatePasswordDTO;
import userapi.dto.UserDTO;
import userapi.exception.CustomExceptionEnum;
import userapi.exception.CustomSystemException;
import userapi.exception.DataNotFoundException;
import userapi.exception.DatabaseException;
import userapi.models.User;
import userapi.util.EmailUtil;

@Service
@Transactional
public class UserService {
	
	 private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao _userDao;
	
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * @param email
	 * @return
	 */
	public UserDTO getByEmail(String email) {
		try {
			User user = _userDao.getByEmail(email);
			if (user != null)
				return new UserDTO(user);
			else
				throw new DataNotFoundException(CustomExceptionEnum.DATA_NOT_FOUND);
		} catch (DataNotFoundException e) {
			log.error("User not exist for email ("+email+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in find user by email: ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}

	}

	/**
	 * @param id
	 * @return
	 */
	public UserDTO getById(Long id) {
		try {
			User user = _userDao.getById(id);
			if (user != null)
				return new UserDTO(user);
			else
				throw new DataNotFoundException(CustomExceptionEnum.DATA_NOT_FOUND);
		} catch (DataNotFoundException e) {
			log.error("User not exist for id ("+id+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in find user by id: ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}


	}


	/**
	 * This method is used to register a user and send registration email to user email for confirmation
	 * @param userDTO
	 * @param appUrl 
	 */
	public void create(final UserDTO userDTO, final String appUrl) {
		try {
			String token = UUID.randomUUID().toString()+"-"+userDTO.getEmail().hashCode();
			User user = new User(userDTO,token);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			_userDao.save(user);
			String emailBody = getEmailBodyForRegistration(appUrl,userDTO.getName(),token);
			emailUtil.sendEmail(userDTO.getEmail(), "Registration Confirmation Email ", emailBody);
		} catch (DataIntegrityViolationException e) {
			log.error("Error in registering User : ",e);
			throw new DatabaseException(CustomExceptionEnum.DUPLICATE_EMAIL);
		} catch (Exception ex) {
			log.error("Error in registering User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}

	}
	
	
	/**
	 * This method will verify link send in email by token
	 * @param token
	 */
	public void confirmUserRegByToken (final String token) {
		
		try {
			User user = _userDao.getByRegToken(token);
			if (user == null) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST);
			} else if (user.getReg_ver_date() != null) {
				throw new DatabaseException(CustomExceptionEnum.REG_LINK_EXPIRED);
			} 
				
			user.setReg_ver_date(new Date());
			user.setUpdate_ts(new Date());
			user.setUpdated_by(user.getEmail());
			_userDao.saveOrUpdate(user);
				
		
		} catch (DatabaseException e) {
			log.error("Error in confirmation User using Email link for token ("+token+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in confirmation User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}
	}

	private String getEmailBodyForRegistration(final String appUrl, final String name,final String token) {
		
		StringBuilder builder = new StringBuilder("Hi ").append(name).append(",").append(System.lineSeparator());
		builder.append("   Thanks for registering with us. To confirm your e-mail address, please click the link below:").append(System.lineSeparator());
		builder.append(appUrl).append("/").append("confirm").append("?token=").append(token);
		builder.append(System.lineSeparator()).append(System.lineSeparator()).append("Thanks").append(System.lineSeparator()).append("Admin");
		
		return builder.toString();
	}

	/**
	 * @param userDTO
	 */
	public void update(final UserDTO userDTO) {
		try {

			User user = _userDao.getById(userDTO.getId());
			if (user == null) {
				throw new DataNotFoundException(CustomExceptionEnum.USER_NOT_EXIST);
			}
			user.setName(userDTO.getName());
			user.setEmail(user.getEmail());
			user.setUpdate_ts(new Date());
			user.setUpdated_by(user.getEmail());
			_userDao.saveOrUpdate(user);
			
		} catch (DataNotFoundException ex ) {
			log.error("User not Exist for id ("+userDTO.getId()+") : ");
			throw ex;
		} catch (DataIntegrityViolationException e) {
			log.error("Error in updating User : ",e);
			throw new DatabaseException(CustomExceptionEnum.DUPLICATE_EMAIL);
		} catch (Exception ex) {
			log.error("Error in updating User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}

	}

	public void sendForgotPasswordLink(final ForgotPasswordDTO forgot, final String appUrl) {
		
		try {
			User user = _userDao.getByEmail(forgot.getEmail());
			if (user == null) 
				throw new DataNotFoundException(CustomExceptionEnum.DATA_NOT_FOUND);
			
			String token = UUID.randomUUID().toString()+"-"+forgot.getEmail().hashCode();
			user.setForgot_pass_date(null);
			user.setForgot_token(token);
			_userDao.saveOrUpdate(user);
			String emailBody = getEmailBodyForForgotPassword(appUrl,user.getName(),token);
			emailUtil.sendEmail(forgot.getEmail(), "Forgot Password Email ", emailBody);
			
				
		} catch (DataNotFoundException e) {
			log.error("User not exist for email ("+forgot.getEmail()+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in find user by email: ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}
		
	}
	
	private String getEmailBodyForForgotPassword(final String appUrl, final String name,final String token) {
		
		StringBuilder builder = new StringBuilder("Hi ").append(name).append(",").append(System.lineSeparator());
		builder.append("   To reset your password, please click the link below:").append(System.lineSeparator());
		builder.append(appUrl).append("/password").append("/verifyToken").append("?token=").append(token);
		builder.append(System.lineSeparator()).append(System.lineSeparator()).append("Thanks").append(System.lineSeparator()).append("Admin");
		
		return builder.toString();
	}

	public void confirmUserByForgotPassToken(final String token) {
		
		try {
			User user = _userDao.getByForgotPassToken(token);
			if (user == null) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST);
			} else if (user.getForgot_pass_date() != null) {
				throw new DatabaseException(CustomExceptionEnum.REG_LINK_EXPIRED);
			} 
		
		} catch (DatabaseException e) {
			log.error("Error in confirmation User using Email link for token ("+token+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in confirmation User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}
	}

	public void updateForgotPassword(UpdatePasswordDTO uPasswordDTO) {
		try {
			
			if (uPasswordDTO.getToken() == null || !uPasswordDTO.getPassword().equals(uPasswordDTO.getConfirmPassword())) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST_PARAM);
			}
			
			
			User user = _userDao.getByForgotPassToken(uPasswordDTO.getToken());
			if (user == null) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST);
			} else if (user.getForgot_pass_date() != null) {
				throw new DatabaseException(CustomExceptionEnum.REG_LINK_EXPIRED);
			} 
			
			user.setPassword(bCryptPasswordEncoder.encode(uPasswordDTO.getPassword()));
			user.setForgot_pass_date(new Date());
			user.setUpdate_ts(new Date());
			user.setUpdated_by(user.getEmail());
			_userDao.saveOrUpdate(user);
		
		} catch (DatabaseException e) {
			log.error("Error in updateForgotPassword ("+uPasswordDTO.getToken()+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in confirmation User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}
	}

	public void changePassword(final String principal, final UpdatePasswordDTO uPasswordDTO) {
		try {
			
			if (!uPasswordDTO.getPassword().equals(uPasswordDTO.getConfirmPassword())) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST_PARAM);
			}
			
			
			User user = _userDao.getByEmail(principal);
			if (user == null) {
				throw new DatabaseException(CustomExceptionEnum.INVALID_REQUEST);
			} 
			
			user.setPassword(bCryptPasswordEncoder.encode(uPasswordDTO.getPassword()));
			user.setUpdate_ts(new Date());
			user.setUpdated_by(user.getEmail());
			_userDao.saveOrUpdate(user);
		
		} catch (DatabaseException e) {
			log.error("User not exist for email ("+principal+") : ");
			throw e;
		} catch (Exception ex) {
			log.error("Error in confirmation User : ",ex);
			throw new CustomSystemException(CustomExceptionEnum.INTERNAL_SYSTEM_EXCEPTION);
		}
		
	}
	
	
}
