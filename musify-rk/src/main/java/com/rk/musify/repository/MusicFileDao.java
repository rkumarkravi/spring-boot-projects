package com.rk.musify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rk.musify.model.dao.MusicFile;

@Repository
public interface MusicFileDao extends JpaRepository<MusicFile, String>{

}
