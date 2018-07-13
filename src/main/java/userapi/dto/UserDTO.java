package userapi.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import userapi.models.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	private long id;

	@Size(min = 3, max = 80)
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" , message = "Invalid Email")
	private String email;

	@Size(min = 3, max = 80)
	private String name;
	
	@Size(min = 3, max = 80)
	private String password;
	
	public UserDTO() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserDTO(User u){
		this.id = u.getId();
		this.email = u.getEmail();
		this.name = u.getName();
	}
	

	
}
