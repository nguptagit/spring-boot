package userapi.dto;

import javax.validation.constraints.Size;

public class UpdatePasswordDTO {
	
	private String token;
	
	@Size(min = 3, max = 80)
	private String password;
	
	@Size(min = 3, max = 80)
	private String confirmPassword;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
