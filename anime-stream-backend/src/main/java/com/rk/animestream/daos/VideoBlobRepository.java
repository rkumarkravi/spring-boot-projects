package com.rk.animestream.daos;

import com.rk.animestream.models.VideoBlob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoBlobRepository extends JpaRepository<VideoBlob, Long> {
}