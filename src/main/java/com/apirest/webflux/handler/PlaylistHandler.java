package com.apirest.webflux.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static org.springframework.web.reactive.function.BodyInserters.*;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import reactor.core.publisher.Mono;

//@Component
public class PlaylistHandler {
	
	@Autowired
	private PlaylistService playlistService;
	
	/*Endpoints with function style.*/
	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(playlistService.findAll(), Playlist.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		
		return ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(playlistService.findById(id), Playlist.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		final Mono<Playlist> playlist = request.bodyToMono(Playlist.class);
		
		return ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fromPublisher(playlist.flatMap(playlistService::save), Playlist.class));		
	}
	
}
