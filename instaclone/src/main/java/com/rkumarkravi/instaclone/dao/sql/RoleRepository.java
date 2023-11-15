package com.rkumarkravi.instaclone.dao.sql;

import com.rkumarkravi.instaclone.entity.IRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<IRole, Long> {

    Set<IRole> findByNameIn(Set<String> roleNames);

    // Other custom queries if needed
}

