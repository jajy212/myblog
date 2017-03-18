package com.jblog.myapp.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 商品
 */
@ApiModel(description = "商品")
@Entity
@Table(name = "goods")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "brief_code")
    private String briefCode;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "unit")
    private String unit;

    @Column(name = "is_service")
    private String isService;

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

    @ManyToOne
    private GoodsType goodsType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Goods goodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public Goods serialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBriefCode() {
        return briefCode;
    }

    public Goods briefCode(String briefCode) {
        this.briefCode = briefCode;
        return this;
    }

    public void setBriefCode(String briefCode) {
        this.briefCode = briefCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public Goods barCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getUnit() {
        return unit;
    }

    public Goods unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIsService() {
        return isService;
    }

    public Goods isService(String isService) {
        this.isService = isService;
        return this;
    }

    public void setIsService(String isService) {
        this.isService = isService;
    }

    public String getRemark() {
        return remark;
    }

    public Goods remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtfield1() {
        return extfield1;
    }

    public Goods extfield1(String extfield1) {
        this.extfield1 = extfield1;
        return this;
    }

    public void setExtfield1(String extfield1) {
        this.extfield1 = extfield1;
    }

    public String getExtfield2() {
        return extfield2;
    }

    public Goods extfield2(String extfield2) {
        this.extfield2 = extfield2;
        return this;
    }

    public void setExtfield2(String extfield2) {
        this.extfield2 = extfield2;
    }

    public String getExtfield3() {
        return extfield3;
    }

    public Goods extfield3(String extfield3) {
        this.extfield3 = extfield3;
        return this;
    }

    public void setExtfield3(String extfield3) {
        this.extfield3 = extfield3;
    }

    public String getExtfield4() {
        return extfield4;
    }

    public Goods extfield4(String extfield4) {
        this.extfield4 = extfield4;
        return this;
    }

    public void setExtfield4(String extfield4) {
        this.extfield4 = extfield4;
    }

    public String getExtfield5() {
        return extfield5;
    }

    public Goods extfield5(String extfield5) {
        this.extfield5 = extfield5;
        return this;
    }

    public void setExtfield5(String extfield5) {
        this.extfield5 = extfield5;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public Goods goodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
        return this;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Goods goods = (Goods) o;
        if (goods.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, goods.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Goods{" +
            "id=" + id +
            ", goodsName='" + goodsName + "'" +
            ", serialNo='" + serialNo + "'" +
            ", briefCode='" + briefCode + "'" +
            ", barCode='" + barCode + "'" +
            ", unit='" + unit + "'" +
            ", isService='" + isService + "'" +
            ", remark='" + remark + "'" +
            ", extfield1='" + extfield1 + "'" +
            ", extfield2='" + extfield2 + "'" +
            ", extfield3='" + extfield3 + "'" +
            ", extfield4='" + extfield4 + "'" +
            ", extfield5='" + extfield5 + "'" +
            '}';
    }
}
