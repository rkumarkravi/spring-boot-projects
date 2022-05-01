package com.rk.musify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.MusicFileDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
	@Autowired
	MusicFileDao musicFileDao;
	public List<MusicFile> getAllMusicByLike(String searchText){
		log.info("serachTextIs:{}",searchText);
		return musicFileDao.findByMusicNameStartingWith(searchText);
	}
}
