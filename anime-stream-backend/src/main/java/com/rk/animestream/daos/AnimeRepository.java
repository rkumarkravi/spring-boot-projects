package com.rk.animestream.daos;

import com.rk.animestream.models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}