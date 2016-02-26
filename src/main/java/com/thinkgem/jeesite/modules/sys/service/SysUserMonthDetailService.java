/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysUserMonthDetail;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.dao.SysUserMonthDetailDao;

/**
 * 当月工作详情Service
 * 
 * @author chl
 * @version 2016-02-01
 */
@Service
@Transactional(readOnly = true)
public class SysUserMonthDetailService extends
		CrudService<SysUserMonthDetailDao, SysUserMonthDetail> {

	public SysUserMonthDetail get(String id) {
		return super.get(id);
	}

	public List<SysUserMonthDetail> findList(
			SysUserMonthDetail sysUserMonthDetail) {
		return super.findList(sysUserMonthDetail);
	}

	public Page<SysUserMonthDetail> findPage(Page<SysUserMonthDetail> page,
			SysUserMonthDetail sysUserMonthDetail) {
		return super.findPage(page, sysUserMonthDetail);
	}

	@Transactional(readOnly = false)
	public void save(SysUserMonthDetail sysUserMonthDetail) {
		super.save(sysUserMonthDetail);
	}

	@Transactional(readOnly = false)
	public void delete(SysUserMonthDetail sysUserMonthDetail) {
		super.delete(sysUserMonthDetail);
	}

	/**
	 * 查询当月工作详情
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public SysUserMonthDetail getMonthDetail(SysUserMonthDetail sysUserMonthDetail , User user) {
		SysUserMonthDetail detail = new SysUserMonthDetail();

		if (StringUtils.isBlank(sysUserMonthDetail.getId())) {
			System.err.println("aaaa");
		}
		else {
			System.err.println("bbbb");
		}
		
		if (user.getId() != null) {

			detail.preInsert();
			detail.setUser(user);
			detail.setCountOvertime(dao.getCountOvertime(user));
			detail.setCountLeave(dao.getCountLeave(user));
			detail.setCountPunch(dao.getCountPunch(user));
			detail.setCountExpense(dao.getCountExpense(user));
			dao.insert(detail);
		}
		
		

		System.err.println("count_overtime:" + detail.getCountOvertime());
		return detail;
	}

}