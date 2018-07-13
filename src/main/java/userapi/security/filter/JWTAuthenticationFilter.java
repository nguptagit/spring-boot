package userapi.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import userapi.dto.ErrorDetail;
import userapi.exception.CustomExceptionEnum;

public class JWTAuthenticationFilter  extends GenericFilterBean {

	 @Override
	  public void doFilter(ServletRequest request,
	             ServletResponse response,
	             FilterChain filterChain)
	      throws IOException, ServletException {
		  try {
			  Authentication authentication = TokenAuthenticationService
				        .getAuthentication((HttpServletRequest)request);

				    SecurityContextHolder.getContext()
				        .setAuthentication(authentication);
				    filterChain.doFilter(request,response);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  HttpServletResponse res = (HttpServletResponse) response;
			  ErrorDetail errorDetail = new ErrorDetail();
				 errorDetail.setReason_code(CustomExceptionEnum.INVALID_TOKEN.getExceptionCode());
				 errorDetail.setReason_desc(CustomExceptionEnum.INVALID_TOKEN.getExceptionDescription());
				 res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				 res.setContentType("application/json");
				 res.getOutputStream().write(restResponseBytes(errorDetail));
				 return;
		  }
	   
	  }
	 
	 
	 private byte[] restResponseBytes(ErrorDetail eErrorResponse) throws IOException {
	        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
	        return serialized.getBytes();
	    }
	}
