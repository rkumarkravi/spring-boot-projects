package com.rk.musify.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.Album;
import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.service.AlbumService;

@RestController
@RequestMapping("/album")
@CrossOrigin(origins = "http://localhost:4200")
public class AlbumController {
	
	@Autowired
	AlbumService albumService;
	
	@RequestMapping("/create")
	public ResponseEntity<Album> insertAlbum(@RequestBody Album album){
		return new ResponseEntity<Album>(albumService.createAlbum(album),HttpStatus.OK);
	}
	
	@RequestMapping("/getAll")
	public ResponseEntity<List<Album>> getAllAlbum(){
		return new ResponseEntity<List<Album>>(albumService.getAllAlbum(),HttpStatus.OK);
	}
	
	@RequestMapping("/getTacks/{aid}")
	public ResponseEntity<Set<MusicFile>> getAllAlbum(@PathVariable("aid") String aid){
		return new ResponseEntity<Set<MusicFile>>(albumService.getAllTracks(aid),HttpStatus.OK);
	}

}
