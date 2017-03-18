package com.jblog.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 会员
 */
@ApiModel(description = "会员")
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "card_no", nullable = false)
    private String cardNo;

    @Column(name = "mem_name")
    private String memName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "balance", precision=10, scale=2)
    private BigDecimal balance;

    @Column(name = "bonus")
    private Integer bonus;

    @Column(name = "remark")
    private String remark;

    @Column(name = "extfield_1")
    private String extfield1;

    @Column(name = "extfield_2")
    private String extfield2;

    @Column(name = "extfield_3")
    private String extfield3;

    @Column(name = "extfield_4")
    private String extfield4;

    @Column(name = "extfield_5")
    private String extfield5;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MemOrder> orders = new HashSet<>();

    @ManyToOne
    private Grade grade;

    @ManyToOne
    private MemberCate category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public Member cardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMemName() {
        return memName;
    }

    public Member memName(String memName) {
        this.memName = memName;
        return this;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getPassword() {
        return password;
    }

    public Member password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public Member phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public Member status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Member birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Member balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getBonus() {
        return bonus;
    }

    public Member bonus(Integer bonus) {
        this.bonus = bonus;
        return this;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public String getRemark() {
        return remark;
    }

    public Member remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtfield1() {
        return extfield1;
    }

    public Member extfield1(String extfield1) {
        this.extfield1 = extfield1;
        return this;
    }

    public void setExtfield1(String extfield1) {
        this.extfield1 = extfield1;
    }

    public String getExtfield2() {
        return extfield2;
    }

    public Member extfield2(String extfield2) {
        this.extfield2 = extfield2;
        return this;
    }

    public void setExtfield2(String extfield2) {
        this.extfield2 = extfield2;
    }

    public String getExtfield3() {
        return extfield3;
    }

    public Member extfield3(String extfield3) {
        this.extfield3 = extfield3;
        return this;
    }

    public void setExtfield3(String extfield3) {
        this.extfield3 = extfield3;
    }

    public String getExtfield4() {
        return extfield4;
    }

    public Member extfield4(String extfield4) {
        this.extfield4 = extfield4;
        return this;
    }

    public void setExtfield4(String extfield4) {
        this.extfield4 = extfield4;
    }

    public String getExtfield5() {
        return extfield5;
    }

    public Member extfield5(String extfield5) {
        this.extfield5 = extfield5;
        return this;
    }

    public void setExtfield5(String extfield5) {
        this.extfield5 = extfield5;
    }

    public Set<MemOrder> getOrders() {
        return orders;
    }

    public Member orders(Set<MemOrder> memOrders) {
        this.orders = memOrders;
        return this;
    }

    public Member addOrder(MemOrder memOrder) {
        this.orders.add(memOrder);
        memOrder.setCreatedBy(this);
        return this;
    }

    public Member removeOrder(MemOrder memOrder) {
        this.orders.remove(memOrder);
        memOrder.setCreatedBy(null);
        return this;
    }

    public void setOrders(Set<MemOrder> memOrders) {
        this.orders = memOrders;
    }

    public Grade getGrade() {
        return grade;
    }

    public Member grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public MemberCate getCategory() {
        return category;
    }

    public Member category(MemberCate memberCate) {
        this.category = memberCate;
        return this;
    }

    public void setCategory(MemberCate memberCate) {
        this.category = memberCate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        if (member.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", cardNo='" + cardNo + "'" +
            ", memName='" + memName + "'" +
            ", password='" + password + "'" +
            ", phone='" + phone + "'" +
            ", status='" + status + "'" +
            ", birthday='" + birthday + "'" +
            ", balance='" + balance + "'" +
            ", bonus='" + bonus + "'" +
            ", remark='" + remark + "'" +
            ", extfield1='" + extfield1 + "'" +
            ", extfield2='" + extfield2 + "'" +
            ", extfield3='" + extfield3 + "'" +
            ", extfield4='" + extfield4 + "'" +
            ", extfield5='" + extfield5 + "'" +
            '}';
    }
}
