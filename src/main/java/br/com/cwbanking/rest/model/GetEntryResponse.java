package br.com.cwbanking.rest.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEntryResponse {
	
	private UUID id;
	
	private UUID cardId;
	
	private UUID cardholderId;
	
	private UUID invoiceId;
	
	private Double value;
	
	private String payee;
	
	private String description;
	
	private LocalDateTime saleDate;

	private LocalDateTime inclusionDate;
}
