package br.com.cwbanking.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Entry;
import br.com.cwbanking.repository.EntryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EntryService {
	
	@Autowired
	private EntryRepository repository;

	public Mono<Entry> launch(final Entry entry) {
		
		entry.setId(UUID.randomUUID());
		entry.setInclusionDate(LocalDateTime.now());
		
		return repository.save(entry);
	}

	public Mono<Entry> findById(final UUID id) {
		
		return repository.findById(id);
	}
	
	public Flux<Entry> find() {
		
		return repository.findAll();
	}
}
