package com.jblog.myapp.repository;

import com.jblog.myapp.domain.MemOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MemOrder entity.
 */
@SuppressWarnings("unused")
public interface MemOrderRepository extends JpaRepository<MemOrder,Long> {

    @Query("select distinct memOrder from MemOrder memOrder left join fetch memOrder.goods")
    List<MemOrder> findAllWithEagerRelationships();

    @Query("select memOrder from MemOrder memOrder left join fetch memOrder.goods where memOrder.id =:id")
    MemOrder findOneWithEagerRelationships(@Param("id") Long id);

}
