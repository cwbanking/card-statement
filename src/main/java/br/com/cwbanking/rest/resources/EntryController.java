package br.com.cwbanking.rest.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cwbanking.domain.model.Entry;
import br.com.cwbanking.domain.service.EntryService;
import br.com.cwbanking.rest.model.GetEntryResponse;
import br.com.cwbanking.rest.model.PostEntryRequest;
import br.com.cwbanking.rest.model.PostEntryResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/entries")
public class EntryController {
	
	@Autowired
	private EntryService service;
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<?>> post(@Valid @RequestBody PostEntryRequest request) {
		
		log.info("[HTTP_POST] /entries request body: {}", request);
		
		return service.launch(Entry.builder()
				.cardholderId(request.getCardholderId())
				.cardId(request.getCardId())
				.value(request.getValue())
				.payee(request.getPayee())
				.description(request.getDescription())
				.saleDate(request.getSaleDate())
				.build()).flatMap(entry -> {
			
			PostEntryResponse response = PostEntryResponse.builder()
					.id(entry.getId())
					.inclusionDate(entry.getInclusionDate())
					.build();
			
			log.info("[HTTP_POST] /entries response body: {}", response);

			EntityModel<PostEntryResponse> responseResource = EntityModel
					.of(response, linkTo(methodOn(EntryController.class).getById(response.getId())).withSelfRel());
			
			try {

				return Mono.just(ResponseEntity
						.created(new URI(responseResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
						.body(responseResource));

			} catch (URISyntaxException e) {

				log.error("[HTTP_POST] /entries error: {}", e.getLocalizedMessage());

				return Mono.just(ResponseEntity
						.internalServerError()
						.body("Unable to post entries " + request.toString()));
			}			
		});
	}
	
	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<?>> getById(@PathVariable UUID id) {

		return service.findById(id).flatMap(entry -> {

			GetEntryResponse response = GetEntryResponse.builder()
					.id(entry.getId())
					.inclusionDate(entry.getInclusionDate())
					.build();

			EntityModel<GetEntryResponse> responseResource = EntityModel.of(response,
					linkTo(methodOn(EntryController.class).getById(response.getId())).withSelfRel());

			return Mono.just(ResponseEntity.ok(responseResource));
		});
	}
	
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<GetEntryResponse> find() {
		
		return service.find().map(entry -> {
			GetEntryResponse response = GetEntryResponse.builder()
					.id(entry.getId())
					.inclusionDate(entry.getInclusionDate())
					.cardholderId(entry.getCardholderId())
					.cardId(entry.getCardId())
					.payee(entry.getPayee())
					.value(entry.getValue())
					.description(entry.getDescription())
					.saleDate(entry.getSaleDate())
					.build();
			
			return response;
		});
	}
}
