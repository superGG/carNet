package com.earl.carnet.web;

import org.hibernate.validator.constraints.Length;

public class MyVo {

	@Length(min = 3,max = 10,message = "传入参数不符合规范")
	private String dodo;

	public String getDodo() {
		return dodo;
	}

	public void setDodo(String dodo) {
		this.dodo = dodo;
	}
	
	
}
