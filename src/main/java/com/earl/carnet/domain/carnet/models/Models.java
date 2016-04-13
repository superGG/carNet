package com.earl.carnet.domain.carnet.models;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 品牌型号实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "models")
public class Models extends AbstractAuditingEntity<Long> implements Serializable {

    private Long brandid;

    private String modelsname;

    public Long getBrandid() {
        return brandid;
    }

    public void setBrandid(Long brandid) {
        this.brandid = brandid;
    }

    public String getModelsname() {
        return modelsname;
    }

    public void setModelsname(String modelsname) {
        this.modelsname = modelsname;
    }

    @Override
    public String toString() {
        return "Models{" +
                "brandid=" + brandid +
                ", modelsname='" + modelsname + '\'' +
                '}';
    }
}
