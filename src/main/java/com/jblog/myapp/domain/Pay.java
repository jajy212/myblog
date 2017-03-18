package com.jblog.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.jblog.myapp.domain.enumeration.PayType;

/**
 * A Pay.
 */
@Entity
@Table(name = "pay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "pay_date")
    private LocalDate payDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "paytype", nullable = false)
    private PayType paytype;

    @ManyToOne
    private MemOrder memOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Pay amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public Pay payDate(LocalDate payDate) {
        this.payDate = payDate;
        return this;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public PayType getPaytype() {
        return paytype;
    }

    public Pay paytype(PayType paytype) {
        this.paytype = paytype;
        return this;
    }

    public void setPaytype(PayType paytype) {
        this.paytype = paytype;
    }

    public MemOrder getMemOrder() {
        return memOrder;
    }

    public Pay memOrder(MemOrder memOrder) {
        this.memOrder = memOrder;
        return this;
    }

    public void setMemOrder(MemOrder memOrder) {
        this.memOrder = memOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pay pay = (Pay) o;
        if (pay.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pay{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", payDate='" + payDate + "'" +
            ", paytype='" + paytype + "'" +
            '}';
    }
}
