package com.jblog.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jblog.myapp.domain.GoodsType;

import com.jblog.myapp.repository.GoodsTypeRepository;
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
 * REST controller for managing GoodsType.
 */
@RestController
@RequestMapping("/api")
public class GoodsTypeResource {

    private final Logger log = LoggerFactory.getLogger(GoodsTypeResource.class);

    private static final String ENTITY_NAME = "goodsType";
        
    private final GoodsTypeRepository goodsTypeRepository;

    public GoodsTypeResource(GoodsTypeRepository goodsTypeRepository) {
        this.goodsTypeRepository = goodsTypeRepository;
    }

    /**
     * POST  /goods-types : Create a new goodsType.
     *
     * @param goodsType the goodsType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new goodsType, or with status 400 (Bad Request) if the goodsType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goods-types")
    @Timed
    public ResponseEntity<GoodsType> createGoodsType(@RequestBody GoodsType goodsType) throws URISyntaxException {
        log.debug("REST request to save GoodsType : {}", goodsType);
        if (goodsType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new goodsType cannot already have an ID")).body(null);
        }
        GoodsType result = goodsTypeRepository.save(goodsType);
        return ResponseEntity.created(new URI("/api/goods-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goods-types : Updates an existing goodsType.
     *
     * @param goodsType the goodsType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated goodsType,
     * or with status 400 (Bad Request) if the goodsType is not valid,
     * or with status 500 (Internal Server Error) if the goodsType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goods-types")
    @Timed
    public ResponseEntity<GoodsType> updateGoodsType(@RequestBody GoodsType goodsType) throws URISyntaxException {
        log.debug("REST request to update GoodsType : {}", goodsType);
        if (goodsType.getId() == null) {
            return createGoodsType(goodsType);
        }
        GoodsType result = goodsTypeRepository.save(goodsType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, goodsType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goods-types : get all the goodsTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of goodsTypes in body
     */
    @GetMapping("/goods-types")
    @Timed
    public List<GoodsType> getAllGoodsTypes() {
        log.debug("REST request to get all GoodsTypes");
        List<GoodsType> goodsTypes = goodsTypeRepository.findAll();
        return goodsTypes;
    }

    /**
     * GET  /goods-types/:id : get the "id" goodsType.
     *
     * @param id the id of the goodsType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the goodsType, or with status 404 (Not Found)
     */
    @GetMapping("/goods-types/{id}")
    @Timed
    public ResponseEntity<GoodsType> getGoodsType(@PathVariable Long id) {
        log.debug("REST request to get GoodsType : {}", id);
        GoodsType goodsType = goodsTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(goodsType));
    }

    /**
     * DELETE  /goods-types/:id : delete the "id" goodsType.
     *
     * @param id the id of the goodsType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goods-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteGoodsType(@PathVariable Long id) {
        log.debug("REST request to delete GoodsType : {}", id);
        goodsTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
