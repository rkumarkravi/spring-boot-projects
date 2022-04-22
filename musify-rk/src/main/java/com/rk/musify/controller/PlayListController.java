package com.rk.musify.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.Playlist;
import com.rk.musify.service.PlayListService;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

	@Autowired
	PlayListService playListService;

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
}
