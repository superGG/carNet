package com.earl.carnet.web;

import org.hibernate.validator.constraints.Length;

public class MyVo {

	@Length(min = 5,max = 100)
	private Integer dodo;

	public Integer getDodo() {
		return dodo;
	}

	public void setDodo(Integer dodo) {
		this.dodo = dodo;
	}
	
	
	
}
