package com.earl.carnet.commons.domain;

import java.util.Date;

/**
 * @author 黄祥谦.
 * @date:2015-12-27 上午9:57:25
 * @version :
 */
public class DateQuery implements QueryObject{

	public Date begdate;
	
	public Date enddate;

	public DateQuery(){}
	
	public DateQuery(Date begdate,Date enddate){
		this.begdate=begdate;
		this.enddate= enddate;
	}
	
	public Date getBegdate() {
		return begdate;
	}

	public static DateQuery getDateQuery(Date begdate,Date enddate){
		if(begdate!=null && enddate!=null){
			return new DateQuery(begdate,enddate);
		}
		return null;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setBegdate(Date begdate) {
		this.begdate = begdate;
	}
}
