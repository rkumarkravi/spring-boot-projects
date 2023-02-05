package com.rk.musify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.service.SearchService;

@RestController
@RequestMapping("/search")
//@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {
	@Autowired
	SearchService searchService;
	@GetMapping("/music/{searchText}")
	public ResponseEntity<List<MusicFile>> getMusics(@PathVariable("searchText") String searchText){
		
		return ResponseEntity.ok(this.searchService.getAllMusicByLike(searchText));
	}
}
