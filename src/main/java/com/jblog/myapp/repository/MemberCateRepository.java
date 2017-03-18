package com.jblog.myapp.repository;

import com.jblog.myapp.domain.MemberCate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MemberCate entity.
 */
@SuppressWarnings("unused")
public interface MemberCateRepository extends JpaRepository<MemberCate,Long> {

}
