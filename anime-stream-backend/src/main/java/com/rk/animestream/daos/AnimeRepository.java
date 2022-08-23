package com.rk.animestream.daos;

import com.rk.animestream.models.Anime;
import com.rk.animestream.models.MyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    @Query("select a from Anime a inner join a.videos videos where a.aId = ?1 and videos.seasonNo = ?2")
    Optional<Anime> getAnimeByIdAndSeasonNo(@NonNull Long aId, @NonNull Long seasonNo);
}