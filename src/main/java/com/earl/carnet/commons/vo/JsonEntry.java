package com.earl.carnet.commons.vo;

import com.earl.carnet.commons.util.GsonUtil;

/**
 * Created by Administrator on 2016/5/1.
 */
public abstract  class JsonEntry {

    public String toJson(){
        return GsonUtil.toJson(this);
    }

}
