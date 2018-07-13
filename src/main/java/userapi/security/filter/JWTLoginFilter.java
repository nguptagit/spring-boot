package userapi.security.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import userapi.dto.ErrorDetail;
import userapi.dto.LoginDTO;
import userapi.exception.CustomExceptionEnum;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter   {
	
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
	    super(new AntPathRequestMatcher(url));
	    setAuthenticationManager(authManager);
	  }

	  @Override
	  public Authentication attemptAuthentication(
	      HttpServletRequest req, HttpServletResponse res)
	      throws AuthenticationException, IOException, ServletException {
		  Authentication authentication = null;
		  try {
			  LoginDTO creds = new ObjectMapper()
				        .readValue(req.getInputStream(), LoginDTO.class);
			  
			  authentication = getAuthenticationManager().authenticate(
				        new UsernamePasswordAuthenticationToken(
					            creds.getUsername(),
					            creds.getPassword(),
					            Collections.emptyList()
					        )
					    );
		  } catch (Exception ex) {
			  ex.printStackTrace();
			 ErrorDetail errorDetail = new ErrorDetail();
			 errorDetail.setReason_code(CustomExceptionEnum.INVALID_CREDENTIAL.getExceptionCode());
			 errorDetail.setReason_desc(CustomExceptionEnum.INVALID_CREDENTIAL.getExceptionDescription());
			 res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 res.setContentType("application/json");
			 res.getOutputStream().write(restResponseBytes(errorDetail));
		  }
		  return authentication;
	  }
	  
	  
	  

	  @Override
	  protected void successfulAuthentication(
	      HttpServletRequest req,
	      HttpServletResponse res, FilterChain chain,
	      Authentication auth) throws IOException, ServletException {
	    TokenAuthenticationService
	        .addAuthentication(res, auth.getName());
	  }
	
	  
	  private byte[] restResponseBytes(ErrorDetail eErrorResponse) throws IOException {
	        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
	        return serialized.getBytes();
	    }
}

