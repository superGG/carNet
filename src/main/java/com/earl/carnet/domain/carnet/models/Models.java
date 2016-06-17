package com.earl.carnet.domain.carnet.models;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 品牌型号实体类.
 * Created by Administrator on 2016/4/4.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "models")
public class Models extends AbstractAuditingEntity<Long> implements Serializable {

    private Long brandId; //品牌id

    private String modelsName;//型号名称



    @AutoID
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
//        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getModelsName() {
        return modelsName;
    }

    public void setModelsName(String modelsName) {
        this.modelsName = modelsName;
    }

    @Override
    public String toString() {
        return "Models{" +
                "id=" + super.getId() +
                ", brandId=" + brandId +
                ", modelsName='" + modelsName + '\'' +
                '}';
    }
}
