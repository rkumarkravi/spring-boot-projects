package com.rk.musify.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.Album;
import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.repository.AlbumDao;

@Service
public class AlbumService {
	@Autowired
	AlbumDao albumDao;

	public Album createAlbum(Album album) {
		if(album.getDescription()==null) {
			album.setDescription(album.getAlbumName());
		}
		return albumDao.save(album);
	}

	public List<Album> getAllAlbum() {

		return albumDao.findAll();
	}
	
	public Set<MusicFile> getAllTracks(String aid) {

		return albumDao.getById(aid).getMusicFiles();
	}
}
