package com.jblog.myapp.repository;

import com.jblog.myapp.domain.Grade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Grade entity.
 */
@SuppressWarnings("unused")
public interface GradeRepository extends JpaRepository<Grade,Long> {

}
