package com.jblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.myapp.domain.MemOrder;
import com.jblog.myapp.service.MemOrderService;
import com.jblog.myapp.web.rest.util.HeaderUtil;
import com.jblog.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MemOrder.
 */
@RestController
@RequestMapping("/api")
public class MemOrderResource {

    private final Logger log = LoggerFactory.getLogger(MemOrderResource.class);

    private static final String ENTITY_NAME = "memOrder";
        
    private final MemOrderService memOrderService;

    public MemOrderResource(MemOrderService memOrderService) {
        this.memOrderService = memOrderService;
    }

    /**
     * POST  /mem-orders : Create a new memOrder.
     *
     * @param memOrder the memOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memOrder, or with status 400 (Bad Request) if the memOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mem-orders")
    @Timed
    public ResponseEntity<MemOrder> createMemOrder(@Valid @RequestBody MemOrder memOrder) throws URISyntaxException {
        log.debug("REST request to save MemOrder : {}", memOrder);
        if (memOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new memOrder cannot already have an ID")).body(null);
        }
        MemOrder result = memOrderService.save(memOrder);
        return ResponseEntity.created(new URI("/api/mem-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mem-orders : Updates an existing memOrder.
     *
     * @param memOrder the memOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memOrder,
     * or with status 400 (Bad Request) if the memOrder is not valid,
     * or with status 500 (Internal Server Error) if the memOrder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mem-orders")
    @Timed
    public ResponseEntity<MemOrder> updateMemOrder(@Valid @RequestBody MemOrder memOrder) throws URISyntaxException {
        log.debug("REST request to update MemOrder : {}", memOrder);
        if (memOrder.getId() == null) {
            return createMemOrder(memOrder);
        }
        MemOrder result = memOrderService.save(memOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mem-orders : get all the memOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memOrders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/mem-orders")
    @Timed
    public ResponseEntity<List<MemOrder>> getAllMemOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MemOrders");
        Page<MemOrder> page = memOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mem-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mem-orders/:id : get the "id" memOrder.
     *
     * @param id the id of the memOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memOrder, or with status 404 (Not Found)
     */
    @GetMapping("/mem-orders/{id}")
    @Timed
    public ResponseEntity<MemOrder> getMemOrder(@PathVariable Long id) {
        log.debug("REST request to get MemOrder : {}", id);
        MemOrder memOrder = memOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memOrder));
    }

    /**
     * DELETE  /mem-orders/:id : delete the "id" memOrder.
     *
     * @param id the id of the memOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mem-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemOrder(@PathVariable Long id) {
        log.debug("REST request to delete MemOrder : {}", id);
        memOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
