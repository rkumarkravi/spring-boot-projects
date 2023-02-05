package com.rk.musify.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.model.dao.Playlist;
import com.rk.musify.service.PlayListService;
import com.rk.musify.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/playlist")
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PlayListController {

	@Autowired
	PlayListService playListService;
	
	@Autowired
	UserService userService;

	@RequestMapping(path = "/create", method = { RequestMethod.POST })
	public ResponseEntity<Playlist> createPlayList(@RequestBody Map<String, String> data) {
		log.info("data {}",data);
		return ResponseEntity.ok(playListService.create(data));
	}

	@RequestMapping(path = {"/addToPlayList","/add"}, method = { RequestMethod.POST })
	public ResponseEntity<Playlist> addToPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.addToPlayList(data.get("pid"), data.get("mid")));
	}

	@RequestMapping(path = {"/removeMusic","/remove"}, method = { RequestMethod.POST })
	public ResponseEntity<Playlist> removeFromPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.removeFromPlayList(data.get("pid"), data.get("mid")));
	}
	
	@RequestMapping(path = {"/getPlayList/{pid}","/get/{pid}"}, method = { RequestMethod.GET })
	public ResponseEntity<Playlist> getPlayList(@PathVariable String pid) {
		return ResponseEntity.ok(playListService.getPlayList(pid));
	}
	
	@RequestMapping(path = {"/getTacks/{pid}"}, method = { RequestMethod.GET })
	public ResponseEntity<Set<MusicFile>> getAllTracks(@PathVariable String pid) {
		return ResponseEntity.ok(playListService.getPlayList(pid).getMusics());
	}
	
	@RequestMapping(path = {"/getAllPlayList/{uid}","/getall/{uid}"}, method = { RequestMethod.GET })
	public ResponseEntity<Set<Playlist>> getAllPlayList(@PathVariable("uid") Integer data) {
		return ResponseEntity.ok(userService.getAllPlayList(data).stream().filter(x->!x.getName().equals("Liked Songs")).collect(Collectors.toSet()));
	}
}
