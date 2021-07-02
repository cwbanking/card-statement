package br.com.cwbanking.rest.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timestamp;
	
	private Integer status;
	
	private String error;
	
	private String cause;
	
	private List<String> details;
}
