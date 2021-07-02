package br.com.cwbanking.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "Entry")
public class Entry {

	@Id
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
