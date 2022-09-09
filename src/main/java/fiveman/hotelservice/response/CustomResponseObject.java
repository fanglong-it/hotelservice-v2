package fiveman.hotelservice.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseObject {
	private int code;
	private String message;
	private Object data;
	
	public CustomResponseObject(int code) {
		this.code = code;
	}
	
	public CustomResponseObject(String message) {
		this.message = message;
	}
	
	public CustomResponseObject(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public CustomResponseObject(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
}
