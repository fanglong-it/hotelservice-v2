package fiveman.hotelservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccessDeniedException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4962585565916982978L;
	private int code;
    private String message;
}
