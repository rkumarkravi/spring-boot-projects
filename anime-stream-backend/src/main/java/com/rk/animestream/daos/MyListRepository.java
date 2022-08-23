package com.rk.animestream.daos;

import com.rk.animestream.models.Anime;
import com.rk.animestream.models.MyList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MyListRepository extends JpaRepository<MyList, Long> {
    @Query("select m from MyList m where m.user.emailId = ?1")
    Optional<MyList> findByUser_EmailId(@NonNull String emailId);

    @Query("select m.animes from MyList m where m.user.emailId = ?1")
    Page<Anime> findAllAnimeInMyList(@NonNull String emailId, Pageable pageable);

}