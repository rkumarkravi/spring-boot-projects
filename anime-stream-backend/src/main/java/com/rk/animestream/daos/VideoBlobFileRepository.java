package com.rk.animestream.daos;

import com.rk.animestream.models.VideoBlobFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoBlobFileRepository extends JpaRepository<VideoBlobFile, Long> {
}