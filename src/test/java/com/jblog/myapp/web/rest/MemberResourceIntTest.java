package com.jblog.myapp.web.rest;

import com.jblog.myapp.JblogApp;

import com.jblog.myapp.domain.Member;
import com.jblog.myapp.repository.MemberRepository;
import com.jblog.myapp.service.MemberService;
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

/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JblogApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final Integer DEFAULT_BONUS = 1;
    private static final Integer UPDATED_BONUS = 2;

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
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemberResource memberResource = new MemberResource(memberService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
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
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .cardNo(DEFAULT_CARD_NO)
            .memName(DEFAULT_MEM_NAME)
            .password(DEFAULT_PASSWORD)
            .phone(DEFAULT_PHONE)
            .status(DEFAULT_STATUS)
            .birthday(DEFAULT_BIRTHDAY)
            .balance(DEFAULT_BALANCE)
            .bonus(DEFAULT_BONUS)
            .remark(DEFAULT_REMARK)
            .extfield1(DEFAULT_EXTFIELD_1)
            .extfield2(DEFAULT_EXTFIELD_2)
            .extfield3(DEFAULT_EXTFIELD_3)
            .extfield4(DEFAULT_EXTFIELD_4)
            .extfield5(DEFAULT_EXTFIELD_5);
        return member;
    }

    @Before
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getCardNo()).isEqualTo(DEFAULT_CARD_NO);
        assertThat(testMember.getMemName()).isEqualTo(DEFAULT_MEM_NAME);
        assertThat(testMember.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMember.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testMember.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testMember.getBonus()).isEqualTo(DEFAULT_BONUS);
        assertThat(testMember.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testMember.getExtfield1()).isEqualTo(DEFAULT_EXTFIELD_1);
        assertThat(testMember.getExtfield2()).isEqualTo(DEFAULT_EXTFIELD_2);
        assertThat(testMember.getExtfield3()).isEqualTo(DEFAULT_EXTFIELD_3);
        assertThat(testMember.getExtfield4()).isEqualTo(DEFAULT_EXTFIELD_4);
        assertThat(testMember.getExtfield5()).isEqualTo(DEFAULT_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCardNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setCardNo(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setStatus(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO.toString())))
            .andExpect(jsonPath("$.[*].memName").value(hasItem(DEFAULT_MEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extfield1").value(hasItem(DEFAULT_EXTFIELD_1.toString())))
            .andExpect(jsonPath("$.[*].extfield2").value(hasItem(DEFAULT_EXTFIELD_2.toString())))
            .andExpect(jsonPath("$.[*].extfield3").value(hasItem(DEFAULT_EXTFIELD_3.toString())))
            .andExpect(jsonPath("$.[*].extfield4").value(hasItem(DEFAULT_EXTFIELD_4.toString())))
            .andExpect(jsonPath("$.[*].extfield5").value(hasItem(DEFAULT_EXTFIELD_5.toString())));
    }

    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.cardNo").value(DEFAULT_CARD_NO.toString()))
            .andExpect(jsonPath("$.memName").value(DEFAULT_MEM_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extfield1").value(DEFAULT_EXTFIELD_1.toString()))
            .andExpect(jsonPath("$.extfield2").value(DEFAULT_EXTFIELD_2.toString()))
            .andExpect(jsonPath("$.extfield3").value(DEFAULT_EXTFIELD_3.toString()))
            .andExpect(jsonPath("$.extfield4").value(DEFAULT_EXTFIELD_4.toString()))
            .andExpect(jsonPath("$.extfield5").value(DEFAULT_EXTFIELD_5.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        updatedMember
            .cardNo(UPDATED_CARD_NO)
            .memName(UPDATED_MEM_NAME)
            .password(UPDATED_PASSWORD)
            .phone(UPDATED_PHONE)
            .status(UPDATED_STATUS)
            .birthday(UPDATED_BIRTHDAY)
            .balance(UPDATED_BALANCE)
            .bonus(UPDATED_BONUS)
            .remark(UPDATED_REMARK)
            .extfield1(UPDATED_EXTFIELD_1)
            .extfield2(UPDATED_EXTFIELD_2)
            .extfield3(UPDATED_EXTFIELD_3)
            .extfield4(UPDATED_EXTFIELD_4)
            .extfield5(UPDATED_EXTFIELD_5);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMember)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testMember.getMemName()).isEqualTo(UPDATED_MEM_NAME);
        assertThat(testMember.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMember.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testMember.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testMember.getBonus()).isEqualTo(UPDATED_BONUS);
        assertThat(testMember.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testMember.getExtfield1()).isEqualTo(UPDATED_EXTFIELD_1);
        assertThat(testMember.getExtfield2()).isEqualTo(UPDATED_EXTFIELD_2);
        assertThat(testMember.getExtfield3()).isEqualTo(UPDATED_EXTFIELD_3);
        assertThat(testMember.getExtfield4()).isEqualTo(UPDATED_EXTFIELD_4);
        assertThat(testMember.getExtfield5()).isEqualTo(UPDATED_EXTFIELD_5);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
    }
}
