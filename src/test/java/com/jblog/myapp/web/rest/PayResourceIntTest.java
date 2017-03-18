package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.Pay;
import com.jblog.myapp.repository.PayRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jblog.myapp.domain.enumeration.PayType;
/**
 * Test class for the PayResource REST controller.
 *
 * @see PayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class PayResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_PAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final PayType DEFAULT_PAYTYPE = PayType.CASH;
    private static final PayType UPDATED_PAYTYPE = PayType.ALIPAY;

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayMockMvc;

    private Pay pay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayResource payResource = new PayResource(payRepository);
        this.restPayMockMvc = MockMvcBuilders.standaloneSetup(payResource)
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
    public static Pay createEntity(EntityManager em) {
        Pay pay = new Pay()
            .amount(DEFAULT_AMOUNT)
            .payDate(DEFAULT_PAY_DATE)
            .paytype(DEFAULT_PAYTYPE);
        return pay;
    }

    @Before
    public void initTest() {
        pay = createEntity(em);
    }

    @Test
    @Transactional
    public void createPay() throws Exception {
        int databaseSizeBeforeCreate = payRepository.findAll().size();

        // Create the Pay
        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pay)))
            .andExpect(status().isCreated());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeCreate + 1);
        Pay testPay = payList.get(payList.size() - 1);
        assertThat(testPay.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPay.getPayDate()).isEqualTo(DEFAULT_PAY_DATE);
        assertThat(testPay.getPaytype()).isEqualTo(DEFAULT_PAYTYPE);
    }

    @Test
    @Transactional
    public void createPayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payRepository.findAll().size();

        // Create the Pay with an existing ID
        pay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pay)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = payRepository.findAll().size();
        // set the field null
        pay.setAmount(null);

        // Create the Pay, which fails.

        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pay)))
            .andExpect(status().isBadRequest());

        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaytypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payRepository.findAll().size();
        // set the field null
        pay.setPaytype(null);

        // Create the Pay, which fails.

        restPayMockMvc.perform(post("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pay)))
            .andExpect(status().isBadRequest());

        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPays() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);

        // Get all the payList
        restPayMockMvc.perform(get("/api/pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pay.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].paytype").value(hasItem(DEFAULT_PAYTYPE.toString())));
    }

    @Test
    @Transactional
    public void getPay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);

        // Get the pay
        restPayMockMvc.perform(get("/api/pays/{id}", pay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pay.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.payDate").value(DEFAULT_PAY_DATE.toString()))
            .andExpect(jsonPath("$.paytype").value(DEFAULT_PAYTYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPay() throws Exception {
        // Get the pay
        restPayMockMvc.perform(get("/api/pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);
        int databaseSizeBeforeUpdate = payRepository.findAll().size();

        // Update the pay
        Pay updatedPay = payRepository.findOne(pay.getId());
        updatedPay
            .amount(UPDATED_AMOUNT)
            .payDate(UPDATED_PAY_DATE)
            .paytype(UPDATED_PAYTYPE);

        restPayMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPay)))
            .andExpect(status().isOk());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeUpdate);
        Pay testPay = payList.get(payList.size() - 1);
        assertThat(testPay.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPay.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testPay.getPaytype()).isEqualTo(UPDATED_PAYTYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPay() throws Exception {
        int databaseSizeBeforeUpdate = payRepository.findAll().size();

        // Create the Pay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayMockMvc.perform(put("/api/pays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pay)))
            .andExpect(status().isCreated());

        // Validate the Pay in the database
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePay() throws Exception {
        // Initialize the database
        payRepository.saveAndFlush(pay);
        int databaseSizeBeforeDelete = payRepository.findAll().size();

        // Get the pay
        restPayMockMvc.perform(delete("/api/pays/{id}", pay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pay> payList = payRepository.findAll();
        assertThat(payList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pay.class);
    }
}
