package com.jblog.myapp.service.impl;

import com.jblog.myapp.service.MemOrderService;
import com.jblog.myapp.domain.MemOrder;
import com.jblog.myapp.repository.MemOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing MemOrder.
 */
@Service
@Transactional
public class MemOrderServiceImpl implements MemOrderService{

    private final Logger log = LoggerFactory.getLogger(MemOrderServiceImpl.class);
    
    private final MemOrderRepository memOrderRepository;

    public MemOrderServiceImpl(MemOrderRepository memOrderRepository) {
        this.memOrderRepository = memOrderRepository;
    }

    /**
     * Save a memOrder.
     *
     * @param memOrder the entity to save
     * @return the persisted entity
     */
    @Override
    public MemOrder save(MemOrder memOrder) {
        log.debug("Request to save MemOrder : {}", memOrder);
        MemOrder result = memOrderRepository.save(memOrder);
        return result;
    }

    /**
     *  Get all the memOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MemOrder> findAll(Pageable pageable) {
        log.debug("Request to get all MemOrders");
        Page<MemOrder> result = memOrderRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one memOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MemOrder findOne(Long id) {
        log.debug("Request to get MemOrder : {}", id);
        MemOrder memOrder = memOrderRepository.findOneWithEagerRelationships(id);
        return memOrder;
    }

    /**
     *  Delete the  memOrder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MemOrder : {}", id);
        memOrderRepository.delete(id);
    }
}
