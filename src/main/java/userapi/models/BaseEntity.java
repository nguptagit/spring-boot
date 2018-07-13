package userapi.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 165584239836293733L;

	private Date insert_ts;
	
	private Date update_ts;
	
	private String created_by;
	
	private String updated_by;

	public Date getInsert_ts() {
		return insert_ts;
	}

	public void setInsert_ts(Date insert_ts) {
		this.insert_ts = insert_ts;
	}

	public Date getUpdate_ts() {
		return update_ts;
	}

	public void setUpdate_ts(Date update_ts) {
		this.update_ts = update_ts;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	
}
