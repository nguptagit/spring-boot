package userapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import userapi.dto.UserDTO;

@Entity
@Table(name="users")
public class User extends BaseEntity {

  /**
	 * 
	 */
	private static final long serialVersionUID = -6336967762382421297L;


 @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
 
  private String email;
  

  private String name;

  private String password;
  
  private String reg_token;
  
  private Date reg_ver_date;
  
  private String forgot_token;
  
  private Date forgot_pass_date;

  public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public User() { }


public User(final UserDTO u) {
	this.id=u.getId();
	this.email = u.getEmail();
	this.name = u.getName();
	this.password = u.getPassword();
}

public User(final UserDTO u, final String token) {
	this.id=u.getId();
	this.email = u.getEmail();
	this.name = u.getName();
	this.password = u.getPassword();
	this.reg_token = token;
	setCreated_by(u.getEmail());
	setInsert_ts(new Date());
}

  public User(long id) { 
    this.id = id;
  }

  public User(final String email, final String name,final String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }
    

  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String value) {
    this.email = value;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

public String getReg_token() {
	return reg_token;
}

public void setReg_token(String reg_token) {
	this.reg_token = reg_token;
}

public Date getReg_ver_date() {
	return reg_ver_date;
}

public void setReg_ver_date(Date reg_ver_date) {
	this.reg_ver_date = reg_ver_date;
}

public String getForgot_token() {
	return forgot_token;
}

public void setForgot_token(String forgot_token) {
	this.forgot_token = forgot_token;
}

public Date getForgot_pass_date() {
	return forgot_pass_date;
}

public void setForgot_pass_date(Date forgot_pass_date) {
	this.forgot_pass_date = forgot_pass_date;
}
  
  
  
} // class User
