package br.com.cwbanking.rest.resources;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.cwbanking.rest.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorMessageHandlerController {

	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentNotValidException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public final ErrorResponse badRequestException(Exception exception, WebRequest request) {
		
		log.error("Handling exception... {}, {}", exception.getCause(), exception.getLocalizedMessage());
		
		final List<String> details = new ArrayList<>();
		String message = null;
		
		if(exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manve = (MethodArgumentNotValidException) exception;
			manve.getAllErrors().stream().forEach(erro -> details.add(erro.getDefaultMessage()));
		}
		
		if(exception instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException mnre = (HttpMessageNotReadableException) exception;
			message = getMessageByExceptionName(mnre.getCause());
			if(mnre.getCause() instanceof InvalidFormatException) {
				message = getMessageByExceptionName(mnre.getCause());
				InvalidFormatException ife = (InvalidFormatException) mnre.getCause();
				ife.getPath().get(0).getFieldName();
				ife.getPath().stream().forEach(path -> details.add("The field '" + path.getFieldName() + "' is invalid."));
			}
		}		
		
		return new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), 
				message == null ? getMessageByExceptionName(exception) : message,
				details.isEmpty() ? null : details);
	}

	private static final String getMessageByExceptionName(Throwable exception) {

		String camelCaseTOSpaces = exception.getClass().getSimpleName().replaceAll(String.format("%s|%s|%s",
				"(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");

		return camelCaseTOSpaces.substring(0, 1).toUpperCase() + camelCaseTOSpaces.substring(1).toLowerCase() + ".";
	}
}
