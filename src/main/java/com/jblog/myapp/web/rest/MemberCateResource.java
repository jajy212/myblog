package com.jblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.myapp.domain.MemberCate;

import com.jblog.myapp.repository.MemberCateRepository;
import com.jblog.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MemberCate.
 */
@RestController
@RequestMapping("/api")
public class MemberCateResource {

    private final Logger log = LoggerFactory.getLogger(MemberCateResource.class);

    private static final String ENTITY_NAME = "memberCate";
        
    private final MemberCateRepository memberCateRepository;

    public MemberCateResource(MemberCateRepository memberCateRepository) {
        this.memberCateRepository = memberCateRepository;
    }

    /**
     * POST  /member-cates : Create a new memberCate.
     *
     * @param memberCate the memberCate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberCate, or with status 400 (Bad Request) if the memberCate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/member-cates")
    @Timed
    public ResponseEntity<MemberCate> createMemberCate(@RequestBody MemberCate memberCate) throws URISyntaxException {
        log.debug("REST request to save MemberCate : {}", memberCate);
        if (memberCate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new memberCate cannot already have an ID")).body(null);
        }
        MemberCate result = memberCateRepository.save(memberCate);
        return ResponseEntity.created(new URI("/api/member-cates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /member-cates : Updates an existing memberCate.
     *
     * @param memberCate the memberCate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberCate,
     * or with status 400 (Bad Request) if the memberCate is not valid,
     * or with status 500 (Internal Server Error) if the memberCate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/member-cates")
    @Timed
    public ResponseEntity<MemberCate> updateMemberCate(@RequestBody MemberCate memberCate) throws URISyntaxException {
        log.debug("REST request to update MemberCate : {}", memberCate);
        if (memberCate.getId() == null) {
            return createMemberCate(memberCate);
        }
        MemberCate result = memberCateRepository.save(memberCate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memberCate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /member-cates : get all the memberCates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of memberCates in body
     */
    @GetMapping("/member-cates")
    @Timed
    public List<MemberCate> getAllMemberCates() {
        log.debug("REST request to get all MemberCates");
        List<MemberCate> memberCates = memberCateRepository.findAll();
        return memberCates;
    }

    /**
     * GET  /member-cates/:id : get the "id" memberCate.
     *
     * @param id the id of the memberCate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberCate, or with status 404 (Not Found)
     */
    @GetMapping("/member-cates/{id}")
    @Timed
    public ResponseEntity<MemberCate> getMemberCate(@PathVariable Long id) {
        log.debug("REST request to get MemberCate : {}", id);
        MemberCate memberCate = memberCateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memberCate));
    }

    /**
     * DELETE  /member-cates/:id : delete the "id" memberCate.
     *
     * @param id the id of the memberCate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/member-cates/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemberCate(@PathVariable Long id) {
        log.debug("REST request to delete MemberCate : {}", id);
        memberCateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
