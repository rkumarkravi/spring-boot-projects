package com.rk.animestream.daos;

import com.rk.animestream.models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    @Query("select a from Anime a inner join a.videos videos where a.aId = ?1 and videos.seasonNo = ?2")
    Optional<Anime> getAnimeByIdAndSeasonNo(@NonNull Long aId, @NonNull Long seasonNo);

    @Query("select distinct a from Anime a where a.dateOfRelease = ?1 and a.released=false order by a.name")
    Set<Anime> findDistinctByDateOfReleaseOrderByNameAsc(Date dateOfRelease);

    @Query("select a from Anime a where lower(a.name) like %?1% order by a.name")
    Set<Anime> findByNameLikeOrderByNameAsc(@NonNull String name);

}