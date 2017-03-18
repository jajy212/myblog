package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.MemberCate;
import com.jblog.myapp.repository.MemberCateRepository;
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
 * Test class for the MemberCateResource REST controller.
 *
 * @see MemberCateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class MemberCateResourceIntTest {

    private static final String DEFAULT_CATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private MemberCateRepository memberCateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberCateMockMvc;

    private MemberCate memberCate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemberCateResource memberCateResource = new MemberCateResource(memberCateRepository);
        this.restMemberCateMockMvc = MockMvcBuilders.standaloneSetup(memberCateResource)
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
    public static MemberCate createEntity(EntityManager em) {
        MemberCate memberCate = new MemberCate()
            .cateName(DEFAULT_CATE_NAME)
            .remark(DEFAULT_REMARK);
        return memberCate;
    }

    @Before
    public void initTest() {
        memberCate = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemberCate() throws Exception {
        int databaseSizeBeforeCreate = memberCateRepository.findAll().size();

        // Create the MemberCate
        restMemberCateMockMvc.perform(post("/api/member-cates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberCate)))
            .andExpect(status().isCreated());

        // Validate the MemberCate in the database
        List<MemberCate> memberCateList = memberCateRepository.findAll();
        assertThat(memberCateList).hasSize(databaseSizeBeforeCreate + 1);
        MemberCate testMemberCate = memberCateList.get(memberCateList.size() - 1);
        assertThat(testMemberCate.getCateName()).isEqualTo(DEFAULT_CATE_NAME);
        assertThat(testMemberCate.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createMemberCateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberCateRepository.findAll().size();

        // Create the MemberCate with an existing ID
        memberCate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberCateMockMvc.perform(post("/api/member-cates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberCate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MemberCate> memberCateList = memberCateRepository.findAll();
        assertThat(memberCateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMemberCates() throws Exception {
        // Initialize the database
        memberCateRepository.saveAndFlush(memberCate);

        // Get all the memberCateList
        restMemberCateMockMvc.perform(get("/api/member-cates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberCate.getId().intValue())))
            .andExpect(jsonPath("$.[*].cateName").value(hasItem(DEFAULT_CATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getMemberCate() throws Exception {
        // Initialize the database
        memberCateRepository.saveAndFlush(memberCate);

        // Get the memberCate
        restMemberCateMockMvc.perform(get("/api/member-cates/{id}", memberCate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memberCate.getId().intValue()))
            .andExpect(jsonPath("$.cateName").value(DEFAULT_CATE_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMemberCate() throws Exception {
        // Get the memberCate
        restMemberCateMockMvc.perform(get("/api/member-cates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemberCate() throws Exception {
        // Initialize the database
        memberCateRepository.saveAndFlush(memberCate);
        int databaseSizeBeforeUpdate = memberCateRepository.findAll().size();

        // Update the memberCate
        MemberCate updatedMemberCate = memberCateRepository.findOne(memberCate.getId());
        updatedMemberCate
            .cateName(UPDATED_CATE_NAME)
            .remark(UPDATED_REMARK);

        restMemberCateMockMvc.perform(put("/api/member-cates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemberCate)))
            .andExpect(status().isOk());

        // Validate the MemberCate in the database
        List<MemberCate> memberCateList = memberCateRepository.findAll();
        assertThat(memberCateList).hasSize(databaseSizeBeforeUpdate);
        MemberCate testMemberCate = memberCateList.get(memberCateList.size() - 1);
        assertThat(testMemberCate.getCateName()).isEqualTo(UPDATED_CATE_NAME);
        assertThat(testMemberCate.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingMemberCate() throws Exception {
        int databaseSizeBeforeUpdate = memberCateRepository.findAll().size();

        // Create the MemberCate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberCateMockMvc.perform(put("/api/member-cates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberCate)))
            .andExpect(status().isCreated());

        // Validate the MemberCate in the database
        List<MemberCate> memberCateList = memberCateRepository.findAll();
        assertThat(memberCateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMemberCate() throws Exception {
        // Initialize the database
        memberCateRepository.saveAndFlush(memberCate);
        int databaseSizeBeforeDelete = memberCateRepository.findAll().size();

        // Get the memberCate
        restMemberCateMockMvc.perform(delete("/api/member-cates/{id}", memberCate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MemberCate> memberCateList = memberCateRepository.findAll();
        assertThat(memberCateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberCate.class);
    }
}
