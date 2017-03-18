package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.Goods;
import com.jblog.myapp.repository.GoodsRepository;
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
 * Test class for the GoodsResource REST controller.
 *
 * @see GoodsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class GoodsResourceIntTest {

    private static final String DEFAULT_GOODS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BRIEF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_IS_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_IS_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTFIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTFIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTFIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTFIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTFIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_EXTFIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTFIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_EXTFIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTFIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_EXTFIELD_5 = "BBBBBBBBBB";

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGoodsMockMvc;

    private Goods goods;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GoodsResource goodsResource = new GoodsResource(goodsRepository);
        this.restGoodsMockMvc = MockMvcBuilders.standaloneSetup(goodsResource)
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
    public static Goods createEntity(EntityManager em) {
        Goods goods = new Goods()
            .goodsName(DEFAULT_GOODS_NAME)
            .serialNo(DEFAULT_SERIAL_NO)
            .briefCode(DEFAULT_BRIEF_CODE)
            .barCode(DEFAULT_BAR_CODE)
            .unit(DEFAULT_UNIT)
            .isService(DEFAULT_IS_SERVICE)
            .remark(DEFAULT_REMARK)
            .extfield1(DEFAULT_EXTFIELD_1)
            .extfield2(DEFAULT_EXTFIELD_2)
            .extfield3(DEFAULT_EXTFIELD_3)
            .extfield4(DEFAULT_EXTFIELD_4)
            .extfield5(DEFAULT_EXTFIELD_5);
        return goods;
    }

    @Before
    public void initTest() {
        goods = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getGoodsName()).isEqualTo(DEFAULT_GOODS_NAME);
        assertThat(testGoods.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testGoods.getBriefCode()).isEqualTo(DEFAULT_BRIEF_CODE);
        assertThat(testGoods.getBarCode()).isEqualTo(DEFAULT_BAR_CODE);
        assertThat(testGoods.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testGoods.getIsService()).isEqualTo(DEFAULT_IS_SERVICE);
        assertThat(testGoods.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testGoods.getExtfield1()).isEqualTo(DEFAULT_EXTFIELD_1);
        assertThat(testGoods.getExtfield2()).isEqualTo(DEFAULT_EXTFIELD_2);
        assertThat(testGoods.getExtfield3()).isEqualTo(DEFAULT_EXTFIELD_3);
        assertThat(testGoods.getExtfield4()).isEqualTo(DEFAULT_EXTFIELD_4);
        assertThat(testGoods.getExtfield5()).isEqualTo(DEFAULT_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void createGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods with an existing ID
        goods.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goodsList
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodsName").value(hasItem(DEFAULT_GOODS_NAME.toString())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO.toString())))
            .andExpect(jsonPath("$.[*].briefCode").value(hasItem(DEFAULT_BRIEF_CODE.toString())))
            .andExpect(jsonPath("$.[*].barCode").value(hasItem(DEFAULT_BAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].isService").value(hasItem(DEFAULT_IS_SERVICE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extfield1").value(hasItem(DEFAULT_EXTFIELD_1.toString())))
            .andExpect(jsonPath("$.[*].extfield2").value(hasItem(DEFAULT_EXTFIELD_2.toString())))
            .andExpect(jsonPath("$.[*].extfield3").value(hasItem(DEFAULT_EXTFIELD_3.toString())))
            .andExpect(jsonPath("$.[*].extfield4").value(hasItem(DEFAULT_EXTFIELD_4.toString())))
            .andExpect(jsonPath("$.[*].extfield5").value(hasItem(DEFAULT_EXTFIELD_5.toString())));
    }

    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.goodsName").value(DEFAULT_GOODS_NAME.toString()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO.toString()))
            .andExpect(jsonPath("$.briefCode").value(DEFAULT_BRIEF_CODE.toString()))
            .andExpect(jsonPath("$.barCode").value(DEFAULT_BAR_CODE.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.isService").value(DEFAULT_IS_SERVICE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extfield1").value(DEFAULT_EXTFIELD_1.toString()))
            .andExpect(jsonPath("$.extfield2").value(DEFAULT_EXTFIELD_2.toString()))
            .andExpect(jsonPath("$.extfield3").value(DEFAULT_EXTFIELD_3.toString()))
            .andExpect(jsonPath("$.extfield4").value(DEFAULT_EXTFIELD_4.toString()))
            .andExpect(jsonPath("$.extfield5").value(DEFAULT_EXTFIELD_5.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = goodsRepository.findOne(goods.getId());
        updatedGoods
            .goodsName(UPDATED_GOODS_NAME)
            .serialNo(UPDATED_SERIAL_NO)
            .briefCode(UPDATED_BRIEF_CODE)
            .barCode(UPDATED_BAR_CODE)
            .unit(UPDATED_UNIT)
            .isService(UPDATED_IS_SERVICE)
            .remark(UPDATED_REMARK)
            .extfield1(UPDATED_EXTFIELD_1)
            .extfield2(UPDATED_EXTFIELD_2)
            .extfield3(UPDATED_EXTFIELD_3)
            .extfield4(UPDATED_EXTFIELD_4)
            .extfield5(UPDATED_EXTFIELD_5);

        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoods)))
            .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getGoodsName()).isEqualTo(UPDATED_GOODS_NAME);
        assertThat(testGoods.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testGoods.getBriefCode()).isEqualTo(UPDATED_BRIEF_CODE);
        assertThat(testGoods.getBarCode()).isEqualTo(UPDATED_BAR_CODE);
        assertThat(testGoods.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testGoods.getIsService()).isEqualTo(UPDATED_IS_SERVICE);
        assertThat(testGoods.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testGoods.getExtfield1()).isEqualTo(UPDATED_EXTFIELD_1);
        assertThat(testGoods.getExtfield2()).isEqualTo(UPDATED_EXTFIELD_2);
        assertThat(testGoods.getExtfield3()).isEqualTo(UPDATED_EXTFIELD_3);
        assertThat(testGoods.getExtfield4()).isEqualTo(UPDATED_EXTFIELD_4);
        assertThat(testGoods.getExtfield5()).isEqualTo(UPDATED_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void updateNonExistingGoods() throws Exception {
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Create the Goods

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);
        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Get the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goods.class);
    }
}
