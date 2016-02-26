/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaLeave;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;

/**
 * 请假申请DAO接口
 * @author chl
 * @version 2016-01-19
 */
@MyBatisDao
public interface OaLeaveDao extends CrudDao<OaLeave> {
	
	public int updateAudit1Text(OaLeave oaLeave);
	
	public int updateAudit2Text(OaLeave oaLeave);
	
	//获取请假总时长
	public String getTotalLeaveTime(OaLeave oaLeave);
	
	//更新流程状态
	public int updateStatus(OaLeave oaLeave);
	
	//hr查看加班状态
	public List<OaLeave> findListByHr(OaLeave oaLeave);
	
	//根据时间区间获取加班总时长
	public String getTotalByTime(OaLeave oaLeave);
}