package com.jblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.myapp.domain.Pay;

import com.jblog.myapp.repository.PayRepository;
import com.jblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pay.
 */
@RestController
@RequestMapping("/api")
public class PayResource {

    private final Logger log = LoggerFactory.getLogger(PayResource.class);

    private static final String ENTITY_NAME = "pay";
        
    private final PayRepository payRepository;

    public PayResource(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    /**
     * POST  /pays : Create a new pay.
     *
     * @param pay the pay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pay, or with status 400 (Bad Request) if the pay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pays")
    @Timed
    public ResponseEntity<Pay> createPay(@Valid @RequestBody Pay pay) throws URISyntaxException {
        log.debug("REST request to save Pay : {}", pay);
        if (pay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pay cannot already have an ID")).body(null);
        }
        Pay result = payRepository.save(pay);
        return ResponseEntity.created(new URI("/api/pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pays : Updates an existing pay.
     *
     * @param pay the pay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pay,
     * or with status 400 (Bad Request) if the pay is not valid,
     * or with status 500 (Internal Server Error) if the pay couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pays")
    @Timed
    public ResponseEntity<Pay> updatePay(@Valid @RequestBody Pay pay) throws URISyntaxException {
        log.debug("REST request to update Pay : {}", pay);
        if (pay.getId() == null) {
            return createPay(pay);
        }
        Pay result = payRepository.save(pay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pays : get all the pays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pays in body
     */
    @GetMapping("/pays")
    @Timed
    public List<Pay> getAllPays() {
        log.debug("REST request to get all Pays");
        List<Pay> pays = payRepository.findAll();
        return pays;
    }

    /**
     * GET  /pays/:id : get the "id" pay.
     *
     * @param id the id of the pay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pay, or with status 404 (Not Found)
     */
    @GetMapping("/pays/{id}")
    @Timed
    public ResponseEntity<Pay> getPay(@PathVariable Long id) {
        log.debug("REST request to get Pay : {}", id);
        Pay pay = payRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pay));
    }

    /**
     * DELETE  /pays/:id : delete the "id" pay.
     *
     * @param id the id of the pay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pays/{id}")
    @Timed
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        log.debug("REST request to delete Pay : {}", id);
        payRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
