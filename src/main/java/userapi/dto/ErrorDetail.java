package userapi.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

	private String reason_code;

	private String reason_desc;
	
	private Map<String, String> fields;

	public ErrorDetail() {
		super();
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getReason_desc() {
		return reason_desc;
	}

	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	


	

}