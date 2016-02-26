/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysUserMonthDetail;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 当月工作详情DAO接口
 * @author chl
 * @version 2016-02-01
 */
@MyBatisDao
public interface SysUserMonthDetailDao extends CrudDao<SysUserMonthDetail> {
	
	/**
	 * 获取当月加班总时长
	 * @param user
	 * @return
	 */
	public String getCountOvertime (User user);
	
	
	/**
	 * 获取当月请假总时长
	 * @param user
	 * @return
	 */
	public String getCountLeave (User user);
	
	
	/**
	 * 获取当月未打卡次数
	 * @param user
	 * @return
	 */
	public String getCountPunch (User user);
	
	
	/**
	 * 获取当月报销总额
	 * @param user
	 * @return
	 */
	public String getCountExpense (User user);
	
}