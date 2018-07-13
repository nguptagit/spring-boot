package userapi.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import userapi.dao.UserDao;
import userapi.models.User;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao _userDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = _userDao.getByEmail(email);
		if (user == null) {
			 throw new UsernameNotFoundException(email);
		}
		
		return new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),Collections.emptyList() );
	}

	
}
