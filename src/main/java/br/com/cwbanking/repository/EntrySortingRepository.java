package br.com.cwbanking.repository;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.cwbanking.domain.model.Entry;
import reactor.core.publisher.Flux;

public interface EntrySortingRepository extends ReactiveSortingRepository<Entry, UUID> {

	Flux<Entry> findByCardholderId(final UUID cardholderId, final PageRequest page);

	Flux<Entry> findByCardholderIdAndInvoiceId(final UUID cardholderId, final UUID invoiceId, final PageRequest page);

	Flux<Entry> findByCardholderIdAndInvoiceIdAndCardId(final UUID cardholderId, final UUID invoiceId, final UUID cardId, final PageRequest page);

	Flux<Entry> findByCardholderIdAndCardId(final UUID cardholderId, final UUID cardId, final PageRequest page);
}