package userapi.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ForgotPasswordDTO {

	@Size(min = 3, max = 80)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" , message = "Invalid Email")
	private String email;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
