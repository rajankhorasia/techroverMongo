package techrovers.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import techrovers.model.CustomError;
import techrovers.model.CustomException;
import techrovers.util.Constant;

@RestControllerAdvice
public class ResponseEntityExceptionHandler {

	
	@ExceptionHandler(CustomException.class)
	  public CustomError customException(CustomException ex, WebRequest request) {
		CustomError message = new CustomError(ex.getCode(), ex.getMessage());
	    return message;
	  }
	
	@ExceptionHandler(Exception.class)
	  public CustomError exception(Exception ex, WebRequest request) {
		CustomError message = new CustomError(Constant.CUSTOM_ERROR_CODE, "Internal server error");
	    return message;
	  }
}
