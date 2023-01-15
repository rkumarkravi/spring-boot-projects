package com.rk.learnity.dao;

import com.rk.learnity.entity.CFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CFileDao extends JpaRepository<CFile, String> {
}