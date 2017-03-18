package com.jblog.myapp.service;

import com.jblog.myapp.domain.MemOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MemOrder.
 */
public interface MemOrderService {

    /**
     * Save a memOrder.
     *
     * @param memOrder the entity to save
     * @return the persisted entity
     */
    MemOrder save(MemOrder memOrder);

    /**
     *  Get all the memOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MemOrder> findAll(Pageable pageable);

    /**
     *  Get the "id" memOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MemOrder findOne(Long id);

    /**
     *  Delete the "id" memOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
