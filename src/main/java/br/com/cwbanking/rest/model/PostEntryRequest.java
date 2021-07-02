package br.com.cwbanking.rest.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostEntryRequest {

	@NotNull(message = "The field 'cardId' is required.")
	private UUID cardId;
	
	@NotNull(message = "The field 'cardholderId' is required.")
	private UUID cardholderId;
	
	@NotNull(message = "The field 'value' is required.")
	private Double value;
	
	@NotBlank(message = "The field 'payee' is required.")
	@Size(min = 2, max = 64, message = "the field 'payee' length must be between 2 and 64")
	private String payee;
	
	@NotBlank(message = "The field 'description' is required.")
	@Size(min = 2, max = 64, message = "the field 'description' length must be between 2 and 128")
	private String description;
	
	@NotNull(message = "The field 'saleDate' is required.")
	private LocalDateTime saleDate;
}
