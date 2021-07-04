package br.com.cwbanking.domain.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Entry;
import br.com.cwbanking.repository.EntryCrudRepository;
import br.com.cwbanking.repository.EntrySortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntryService {
	
	@Autowired
	private EntryCrudRepository crudRepository;
	
	@Autowired
	private EntrySortingRepository sortingRepository;

	public Mono<Entry> launch(final Entry entry) {
		
		entry.setId(UUID.randomUUID());
		entry.setInclusionDate(LocalDateTime.now());
		
		return crudRepository.save(entry);
	}

	public Mono<Entry> findById(final UUID id) {
		
		return crudRepository.findById(id);
	}
	
	public Flux<Entry> find(UUID cardholderId, UUID invoiceId, UUID cardId, PageRequest page) {
		
		if(Objects.nonNull(invoiceId) && Objects.isNull(cardId)) {
			
			return sortingRepository.findByCardholderIdAndInvoiceId(cardholderId, invoiceId, page);
			
		} else if (Objects.nonNull(cardId) && Objects.isNull(invoiceId)) {
			
			return sortingRepository.findByCardholderIdAndCardId(cardholderId, cardId, page);
			
		} else if (Objects.nonNull(cardId) && Objects.nonNull(invoiceId)) {
		
			return sortingRepository.findByCardholderIdAndInvoiceIdAndCardId(cardholderId, invoiceId, cardId, page);
		}
		
		return sortingRepository.findByCardholderId(cardholderId, page);
	}
}
