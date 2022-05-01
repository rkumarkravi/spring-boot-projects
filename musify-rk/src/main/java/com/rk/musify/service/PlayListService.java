package com.rk.musify.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.MusicFile;
import com.rk.musify.model.dao.Playlist;
import com.rk.musify.model.dao.User;
import com.rk.musify.repository.MusicFileDao;
import com.rk.musify.repository.PlayListDao;
import com.rk.musify.repository.UserDao;

@Service
public class PlayListService {
	@Autowired
	UserDao userDao;

	@Autowired
	PlayListDao playListDao;

	@Autowired
	MusicFileDao musicFileDao;

	public Playlist create(Map<String, String> playlistData) {
		Playlist playList = playListDao.save(new Playlist(playlistData.get("playlistName")));
		MusicFile mFile=musicFileDao.getById(playlistData.get("mid"));
		playList.getMusics().add(mFile);
		playList=playListDao.save(playList);
		User user = userDao.findById(Integer.parseInt(playlistData.get("userId"))).get();
		user.getPlaylists().add(playList);
		userDao.save(user);
		return playList;
	}

	public Playlist addToPlayList(String playListId, String musicId) {
		Playlist playlist = playListDao.getById(playListId);
		playlist.getMusics().add(musicFileDao.getById(musicId));
		playListDao.save(playlist);
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
