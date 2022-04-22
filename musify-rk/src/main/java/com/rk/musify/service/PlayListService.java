package com.rk.musify.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.Playlist;
import com.rk.musify.repository.MusicFileDao;
import com.rk.musify.repository.PlayListDao;

@Service
public class PlayListService {

	@Autowired
	PlayListDao playListDao;

	@Autowired
	MusicFileDao musicFileDao;

	public Playlist create(Map<String, String> playlistName) {
		return playListDao.save(new Playlist());
	}

	public Playlist addToPlayList(String playListId, String musicId) {
		Playlist playlist = playListDao.getById(playListId);
		playlist.getMusics().add(musicFileDao.getById(musicId));
		return playlist;
	}

	public Playlist removeFromPlayList(String playListId, String musicId) {
		Playlist playlist = playListDao.getById(playListId);
		playlist.getMusics().remove(musicFileDao.getById(musicId));
		return playlist;
	}
	
	public Playlist getPlayList(String playListId) {
		Playlist playlist = playListDao.getById(playListId);
		return playlist;
	}

}
