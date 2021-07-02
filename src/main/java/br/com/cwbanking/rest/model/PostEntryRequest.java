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

	@NotNull
	private UUID cardId;
	
	@NotNull
	private UUID cardholderId;
	
	@NotNull
	private Double value;
	
	@NotBlank
	@Size(min = 2, max = 64)
	private String payee;
	
	@NotBlank
	@Size(min = 2, max = 128)
	private String description;
	
	@NotNull
	private LocalDateTime saleDate;
}
