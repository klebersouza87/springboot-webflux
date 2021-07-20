package com.apirest.webflux.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.services.PlaylistService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	
	@GetMapping
	public Flux<Playlist> getAll() {
		return playlistService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Playlist> get(@PathVariable String id) {
		return playlistService.findById(id);
	}
	
	@PostMapping
	public Mono<Playlist> save(@RequestBody Playlist playlist) {
		return playlistService.save(playlist);
	}
	
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));
		Flux<Playlist> events = playlistService.findAll();
		System.out.println("Event stream started!");
		return Flux.zip(interval, events);		
	}
	
}
