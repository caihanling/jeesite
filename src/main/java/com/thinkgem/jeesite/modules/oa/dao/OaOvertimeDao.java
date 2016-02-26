/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;

/**
 * 加班申请DAO接口
 * @author chl
 * @version 2016-01-16
 */
@MyBatisDao
public interface OaOvertimeDao extends CrudDao<OaOvertime> {
	
	public int updateAudit1Text(OaOvertime oaOvertime);
	
	public int updateAudit2Text(OaOvertime oaOvertime);
	
	public int confirmOvertime(OaOvertime oaOvertime);
	
	//获取当月加班总时长加班
	public float getTotalTime(OaOvertime oaOvertime);
	
	//根据时间区间获取加班总时长
	public String getTotalByTime(OaOvertime oaOvertime);
	
	//修改流程状态
	public int updateStatus(OaOvertime oaOvertime);
	
	//hr查看加班状态
	public List<OaOvertime> findListByHr(OaOvertime oaOvertime);
	
}