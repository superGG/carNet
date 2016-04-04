package com.earl.carnet.domain.sercurity.brand;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 汽车品牌实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name ="brand")
public class Brand extends AbstractAuditingEntity<Long> implements Serializable {

    private String brandname;//品牌名称

    private String mark;//品牌标志

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
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
                "brandname='" + brandname + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
