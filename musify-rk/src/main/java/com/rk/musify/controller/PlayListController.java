package com.rk.musify.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.Playlist;
import com.rk.musify.service.PlayListService;
import com.rk.musify.service.UserService;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

	@Autowired
	PlayListService playListService;
	
	@Autowired
	UserService userService;

	@RequestMapping(path = "/create", method = { RequestMethod.POST })
	public ResponseEntity<Playlist> createPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.create(data));
	}

	@RequestMapping(path = "/addToPlayList", method = { RequestMethod.POST })
	public ResponseEntity<Playlist> addToPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.addToPlayList(data.get("pid"), data.get("mid")));
	}

	@RequestMapping(path = "/removeMusic", method = { RequestMethod.POST })
	public ResponseEntity<Playlist> removeFromPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.removeFromPlayList(data.get("pid"), data.get("mid")));
	}
	
	@RequestMapping(path = "/getPlayList", method = { RequestMethod.GET })
	public ResponseEntity<Playlist> getPlayList(@RequestBody Map<String, String> data) {
		return ResponseEntity.ok(playListService.getPlayList(data.get("pid")));
	}
	
	@RequestMapping(path = "/getAllPlayList/{uid}", method = { RequestMethod.GET })
	public ResponseEntity<Set<Playlist>> getAllPlayList(@PathVariable Integer data) {
		return ResponseEntity.ok(userService.getAllPlayList(data));
	}
}
