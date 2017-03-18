package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.GoodsType;
import com.jblog.myapp.repository.GoodsTypeRepository;
import com.jblog.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GoodsTypeResource REST controller.
 *
 * @see GoodsTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class GoodsTypeResourceIntTest {

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private GoodsTypeRepository goodsTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGoodsTypeMockMvc;

    private GoodsType goodsType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GoodsTypeResource goodsTypeResource = new GoodsTypeResource(goodsTypeRepository);
        this.restGoodsTypeMockMvc = MockMvcBuilders.standaloneSetup(goodsTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodsType createEntity(EntityManager em) {
        GoodsType goodsType = new GoodsType()
            .parentId(DEFAULT_PARENT_ID)
            .typeName(DEFAULT_TYPE_NAME)
            .remark(DEFAULT_REMARK);
        return goodsType;
    }

    @Before
    public void initTest() {
        goodsType = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoodsType() throws Exception {
        int databaseSizeBeforeCreate = goodsTypeRepository.findAll().size();

        // Create the GoodsType
        restGoodsTypeMockMvc.perform(post("/api/goods-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsType)))
            .andExpect(status().isCreated());

        // Validate the GoodsType in the database
        List<GoodsType> goodsTypeList = goodsTypeRepository.findAll();
        assertThat(goodsTypeList).hasSize(databaseSizeBeforeCreate + 1);
        GoodsType testGoodsType = goodsTypeList.get(goodsTypeList.size() - 1);
        assertThat(testGoodsType.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testGoodsType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testGoodsType.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createGoodsTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsTypeRepository.findAll().size();

        // Create the GoodsType with an existing ID
        goodsType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsTypeMockMvc.perform(post("/api/goods-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GoodsType> goodsTypeList = goodsTypeRepository.findAll();
        assertThat(goodsTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGoodsTypes() throws Exception {
        // Initialize the database
        goodsTypeRepository.saveAndFlush(goodsType);

        // Get all the goodsTypeList
        restGoodsTypeMockMvc.perform(get("/api/goods-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodsType.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getGoodsType() throws Exception {
        // Initialize the database
        goodsTypeRepository.saveAndFlush(goodsType);

        // Get the goodsType
        restGoodsTypeMockMvc.perform(get("/api/goods-types/{id}", goodsType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goodsType.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoodsType() throws Exception {
        // Get the goodsType
        restGoodsTypeMockMvc.perform(get("/api/goods-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodsType() throws Exception {
        // Initialize the database
        goodsTypeRepository.saveAndFlush(goodsType);
        int databaseSizeBeforeUpdate = goodsTypeRepository.findAll().size();

        // Update the goodsType
        GoodsType updatedGoodsType = goodsTypeRepository.findOne(goodsType.getId());
        updatedGoodsType
            .parentId(UPDATED_PARENT_ID)
            .typeName(UPDATED_TYPE_NAME)
            .remark(UPDATED_REMARK);

        restGoodsTypeMockMvc.perform(put("/api/goods-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoodsType)))
            .andExpect(status().isOk());

        // Validate the GoodsType in the database
        List<GoodsType> goodsTypeList = goodsTypeRepository.findAll();
        assertThat(goodsTypeList).hasSize(databaseSizeBeforeUpdate);
        GoodsType testGoodsType = goodsTypeList.get(goodsTypeList.size() - 1);
        assertThat(testGoodsType.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testGoodsType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testGoodsType.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingGoodsType() throws Exception {
        int databaseSizeBeforeUpdate = goodsTypeRepository.findAll().size();

        // Create the GoodsType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGoodsTypeMockMvc.perform(put("/api/goods-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodsType)))
            .andExpect(status().isCreated());

        // Validate the GoodsType in the database
        List<GoodsType> goodsTypeList = goodsTypeRepository.findAll();
        assertThat(goodsTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGoodsType() throws Exception {
        // Initialize the database
        goodsTypeRepository.saveAndFlush(goodsType);
        int databaseSizeBeforeDelete = goodsTypeRepository.findAll().size();

        // Get the goodsType
        restGoodsTypeMockMvc.perform(delete("/api/goods-types/{id}", goodsType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GoodsType> goodsTypeList = goodsTypeRepository.findAll();
        assertThat(goodsTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodsType.class);
    }
}
