package br.com.cwbanking.rest.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostEntryResponse {
	
	private UUID id;

	private LocalDateTime inclusionDate;
}
