package com.rk.animestream.daos;

import com.rk.animestream.models.MyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyListRepository extends JpaRepository<MyList, Long> {
}