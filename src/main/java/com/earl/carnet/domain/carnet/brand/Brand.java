package com.earl.carnet.domain.carnet.brand;

import java.io.Serializable;

import javax.persistence.Table;

import org.beetl.sql.core.annotatoin.AutoID;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 汽车品牌实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name ="brand")
public class Brand extends AbstractAuditingEntity<Long> implements Serializable {

    private String brandName;//品牌名称

    private String mark;//品牌标志

    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + super.getId() +
                ", brandName='" + brandName + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
