package com.jblog.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MemOrder.
 */
@Entity
@Table(name = "mem_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Column(name = "order_type")
    private String orderType;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

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

    @OneToMany(mappedBy = "memOrder")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pay> pays = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mem_order_goods",
               joinColumns = @JoinColumn(name="mem_orders_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="goods_id", referencedColumnName="id"))
    private Set<Goods> goods = new HashSet<>();

    @ManyToOne
    private Member createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public MemOrder orderCode(String orderCode) {
        this.orderCode = orderCode;
        return this;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public MemOrder orderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public MemOrder createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public MemOrder status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public MemOrder lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getRemark() {
        return remark;
    }

    public MemOrder remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtfield1() {
        return extfield1;
    }

    public MemOrder extfield1(String extfield1) {
        this.extfield1 = extfield1;
        return this;
    }

    public void setExtfield1(String extfield1) {
        this.extfield1 = extfield1;
    }

    public String getExtfield2() {
        return extfield2;
    }

    public MemOrder extfield2(String extfield2) {
        this.extfield2 = extfield2;
        return this;
    }

    public void setExtfield2(String extfield2) {
        this.extfield2 = extfield2;
    }

    public String getExtfield3() {
        return extfield3;
    }

    public MemOrder extfield3(String extfield3) {
        this.extfield3 = extfield3;
        return this;
    }

    public void setExtfield3(String extfield3) {
        this.extfield3 = extfield3;
    }

    public String getExtfield4() {
        return extfield4;
    }

    public MemOrder extfield4(String extfield4) {
        this.extfield4 = extfield4;
        return this;
    }

    public void setExtfield4(String extfield4) {
        this.extfield4 = extfield4;
    }

    public String getExtfield5() {
        return extfield5;
    }

    public MemOrder extfield5(String extfield5) {
        this.extfield5 = extfield5;
        return this;
    }

    public void setExtfield5(String extfield5) {
        this.extfield5 = extfield5;
    }

    public Set<Pay> getPays() {
        return pays;
    }

    public MemOrder pays(Set<Pay> pays) {
        this.pays = pays;
        return this;
    }

    public MemOrder addPay(Pay pay) {
        this.pays.add(pay);
        pay.setMemOrder(this);
        return this;
    }

    public MemOrder removePay(Pay pay) {
        this.pays.remove(pay);
        pay.setMemOrder(null);
        return this;
    }

    public void setPays(Set<Pay> pays) {
        this.pays = pays;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public MemOrder goods(Set<Goods> goods) {
        this.goods = goods;
        return this;
    }

    public MemOrder addGoods(Goods goods) {
        this.goods.add(goods);
        return this;
    }

    public MemOrder removeGoods(Goods goods) {
        this.goods.remove(goods);
        return this;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public MemOrder createdBy(Member member) {
        this.createdBy = member;
        return this;
    }

    public void setCreatedBy(Member member) {
        this.createdBy = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemOrder memOrder = (MemOrder) o;
        if (memOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, memOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MemOrder{" +
            "id=" + id +
            ", orderCode='" + orderCode + "'" +
            ", orderType='" + orderType + "'" +
            ", createdDate='" + createdDate + "'" +
            ", status='" + status + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", remark='" + remark + "'" +
            ", extfield1='" + extfield1 + "'" +
            ", extfield2='" + extfield2 + "'" +
            ", extfield3='" + extfield3 + "'" +
            ", extfield4='" + extfield4 + "'" +
            ", extfield5='" + extfield5 + "'" +
            '}';
    }
}
