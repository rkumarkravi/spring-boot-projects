package com.rk.musify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rk.musify.model.dao.MusicFile;

@Repository
public interface MusicFileDao extends JpaRepository<MusicFile, String>{
	List<MusicFile> findByMusicNameLike(String musicName);
	@Query("SELECT m FROM MusicFile m WHERE m.musicName like %?1%")
	List<MusicFile> findByMusicNameStartingWith(String musicName);
}
