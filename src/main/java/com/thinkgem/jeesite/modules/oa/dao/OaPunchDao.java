/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaPunch;

/**
 * 未打卡申请DAO接口
 * @author chl
 * @version 2016-01-22
 */
@MyBatisDao
public interface OaPunchDao extends CrudDao<OaPunch> {
	
	public int updateAudit1Text(OaPunch oaPunch);
	
	public int updateAudit2Text(OaPunch oaPunch);
	
	//修改流程状态
	public int updateStatus(OaPunch oaPunch);
	
	//hr查看未打卡状态
	public List<OaPunch> findListByHr(OaPunch oaPunch);
	
	//根据时间区间获取未打卡总次数
	public String getTotalByTime(OaPunch oaPunch);
		

}