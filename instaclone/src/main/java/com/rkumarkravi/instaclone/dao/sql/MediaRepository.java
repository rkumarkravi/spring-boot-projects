package com.rkumarkravi.instaclone.dao.sql;

import com.rkumarkravi.instaclone.entity.IMedia;
import com.rkumarkravi.instaclone.entity.IUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MediaRepository extends JpaRepository<IMedia, Long> {

    List<IMedia> findByUser(IUser user);

    // Other custom queries if needed
}

