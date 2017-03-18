package com.jblog.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MemberCate.
 */
@Entity
@Table(name = "member_cate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberCate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cate_name")
    private String cateName;

    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public MemberCate cateName(String cateName) {
        this.cateName = cateName;
        return this;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getRemark() {
        return remark;
    }

    public MemberCate remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberCate memberCate = (MemberCate) o;
        if (memberCate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, memberCate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MemberCate{" +
            "id=" + id +
            ", cateName='" + cateName + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
