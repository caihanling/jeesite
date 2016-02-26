/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaExpense;

/**
 * 报销申请DAO接口
 * @author chl
 * @version 2016-01-23
 */
@MyBatisDao
public interface OaExpenseDao extends CrudDao<OaExpense> {
	
	public int updateAudit1Text(OaExpense oaExpense);
	
	public int updateAudit2Text(OaExpense oaExpense);
	
	public int updateAudit3Text(OaExpense oaExpense);
	
}