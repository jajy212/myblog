package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.MemOrder;
import com.jblog.myapp.repository.MemOrderRepository;
import com.jblog.myapp.service.MemOrderService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MemOrderResource REST controller.
 *
 * @see MemOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class MemOrderResourceIntTest {

    private static final String DEFAULT_ORDER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private MemOrderRepository memOrderRepository;

    @Autowired
    private MemOrderService memOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemOrderMockMvc;

    private MemOrder memOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemOrderResource memOrderResource = new MemOrderResource(memOrderService);
        this.restMemOrderMockMvc = MockMvcBuilders.standaloneSetup(memOrderResource)
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
    public static MemOrder createEntity(EntityManager em) {
        MemOrder memOrder = new MemOrder()
            .orderCode(DEFAULT_ORDER_CODE)
            .orderType(DEFAULT_ORDER_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .status(DEFAULT_STATUS)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .remark(DEFAULT_REMARK)
            .extfield1(DEFAULT_EXTFIELD_1)
            .extfield2(DEFAULT_EXTFIELD_2)
            .extfield3(DEFAULT_EXTFIELD_3)
            .extfield4(DEFAULT_EXTFIELD_4)
            .extfield5(DEFAULT_EXTFIELD_5);
        return memOrder;
    }

    @Before
    public void initTest() {
        memOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemOrder() throws Exception {
        int databaseSizeBeforeCreate = memOrderRepository.findAll().size();

        // Create the MemOrder
        restMemOrderMockMvc.perform(post("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isCreated());

        // Validate the MemOrder in the database
        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MemOrder testMemOrder = memOrderList.get(memOrderList.size() - 1);
        assertThat(testMemOrder.getOrderCode()).isEqualTo(DEFAULT_ORDER_CODE);
        assertThat(testMemOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testMemOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMemOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMemOrder.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testMemOrder.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMemOrder.getExtfield1()).isEqualTo(DEFAULT_EXTFIELD_1);
        assertThat(testMemOrder.getExtfield2()).isEqualTo(DEFAULT_EXTFIELD_2);
        assertThat(testMemOrder.getExtfield3()).isEqualTo(DEFAULT_EXTFIELD_3);
        assertThat(testMemOrder.getExtfield4()).isEqualTo(DEFAULT_EXTFIELD_4);
        assertThat(testMemOrder.getExtfield5()).isEqualTo(DEFAULT_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void createMemOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memOrderRepository.findAll().size();

        // Create the MemOrder with an existing ID
        memOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemOrderMockMvc.perform(post("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = memOrderRepository.findAll().size();
        // set the field null
        memOrder.setOrderCode(null);

        // Create the MemOrder, which fails.

        restMemOrderMockMvc.perform(post("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isBadRequest());

        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = memOrderRepository.findAll().size();
        // set the field null
        memOrder.setCreatedDate(null);

        // Create the MemOrder, which fails.

        restMemOrderMockMvc.perform(post("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isBadRequest());

        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = memOrderRepository.findAll().size();
        // set the field null
        memOrder.setStatus(null);

        // Create the MemOrder, which fails.

        restMemOrderMockMvc.perform(post("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isBadRequest());

        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemOrders() throws Exception {
        // Initialize the database
        memOrderRepository.saveAndFlush(memOrder);

        // Get all the memOrderList
        restMemOrderMockMvc.perform(get("/api/mem-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderCode").value(hasItem(DEFAULT_ORDER_CODE.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extfield1").value(hasItem(DEFAULT_EXTFIELD_1.toString())))
            .andExpect(jsonPath("$.[*].extfield2").value(hasItem(DEFAULT_EXTFIELD_2.toString())))
            .andExpect(jsonPath("$.[*].extfield3").value(hasItem(DEFAULT_EXTFIELD_3.toString())))
            .andExpect(jsonPath("$.[*].extfield4").value(hasItem(DEFAULT_EXTFIELD_4.toString())))
            .andExpect(jsonPath("$.[*].extfield5").value(hasItem(DEFAULT_EXTFIELD_5.toString())));
    }

    @Test
    @Transactional
    public void getMemOrder() throws Exception {
        // Initialize the database
        memOrderRepository.saveAndFlush(memOrder);

        // Get the memOrder
        restMemOrderMockMvc.perform(get("/api/mem-orders/{id}", memOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderCode").value(DEFAULT_ORDER_CODE.toString()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extfield1").value(DEFAULT_EXTFIELD_1.toString()))
            .andExpect(jsonPath("$.extfield2").value(DEFAULT_EXTFIELD_2.toString()))
            .andExpect(jsonPath("$.extfield3").value(DEFAULT_EXTFIELD_3.toString()))
            .andExpect(jsonPath("$.extfield4").value(DEFAULT_EXTFIELD_4.toString()))
            .andExpect(jsonPath("$.extfield5").value(DEFAULT_EXTFIELD_5.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMemOrder() throws Exception {
        // Get the memOrder
        restMemOrderMockMvc.perform(get("/api/mem-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemOrder() throws Exception {
        // Initialize the database
        memOrderService.save(memOrder);

        int databaseSizeBeforeUpdate = memOrderRepository.findAll().size();

        // Update the memOrder
        MemOrder updatedMemOrder = memOrderRepository.findOne(memOrder.getId());
        updatedMemOrder
            .orderCode(UPDATED_ORDER_CODE)
            .orderType(UPDATED_ORDER_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .remark(UPDATED_REMARK)
            .extfield1(UPDATED_EXTFIELD_1)
            .extfield2(UPDATED_EXTFIELD_2)
            .extfield3(UPDATED_EXTFIELD_3)
            .extfield4(UPDATED_EXTFIELD_4)
            .extfield5(UPDATED_EXTFIELD_5);

        restMemOrderMockMvc.perform(put("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemOrder)))
            .andExpect(status().isOk());

        // Validate the MemOrder in the database
        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeUpdate);
        MemOrder testMemOrder = memOrderList.get(memOrderList.size() - 1);
        assertThat(testMemOrder.getOrderCode()).isEqualTo(UPDATED_ORDER_CODE);
        assertThat(testMemOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testMemOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMemOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMemOrder.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testMemOrder.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMemOrder.getExtfield1()).isEqualTo(UPDATED_EXTFIELD_1);
        assertThat(testMemOrder.getExtfield2()).isEqualTo(UPDATED_EXTFIELD_2);
        assertThat(testMemOrder.getExtfield3()).isEqualTo(UPDATED_EXTFIELD_3);
        assertThat(testMemOrder.getExtfield4()).isEqualTo(UPDATED_EXTFIELD_4);
        assertThat(testMemOrder.getExtfield5()).isEqualTo(UPDATED_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void updateNonExistingMemOrder() throws Exception {
        int databaseSizeBeforeUpdate = memOrderRepository.findAll().size();

        // Create the MemOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemOrderMockMvc.perform(put("/api/mem-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memOrder)))
            .andExpect(status().isCreated());

        // Validate the MemOrder in the database
        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMemOrder() throws Exception {
        // Initialize the database
        memOrderService.save(memOrder);

        int databaseSizeBeforeDelete = memOrderRepository.findAll().size();

        // Get the memOrder
        restMemOrderMockMvc.perform(delete("/api/mem-orders/{id}", memOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MemOrder> memOrderList = memOrderRepository.findAll();
        assertThat(memOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemOrder.class);
    }
}
