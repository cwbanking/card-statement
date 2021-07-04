package br.com.cwbanking.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.cwbanking.domain.model.Entry;

public interface EntryCrudRepository extends ReactiveCrudRepository<Entry, UUID> {

}