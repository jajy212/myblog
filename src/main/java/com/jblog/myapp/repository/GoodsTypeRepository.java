package com.jblog.myapp.repository;

import com.jblog.myapp.domain.GoodsType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GoodsType entity.
 */
@SuppressWarnings("unused")
public interface GoodsTypeRepository extends JpaRepository<GoodsType,Long> {

}
