package com.earl.carnet.web;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.earl.carnet.interceptor.StringEscapeEditor;

/**
 * @author 黄祥谦.
 * @date:2016-1-15 上午11:39:19
 * @version :
 */
public abstract class BaseController {

		@InitBinder
		public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true,false,false));
		}
}
