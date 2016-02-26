/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 当月工作详情Entity
 * @author chl
 * @version 2016-02-01
 */
public class SysUserMonthDetail extends DataEntity<SysUserMonthDetail> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户编号
	private Office office;		// 归属部门
	private String countOvertime;		// 加班总时长
	private String countLeave;		// 请假总时长
	private String countPunch;		// 未打卡次数
	private String countExpense;		// 报销总额
	private String backup1;		// 备用字段1
	private String backup2;		// 备用字段2
	
	public SysUserMonthDetail() {
		super();
	}

	public SysUserMonthDetail(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public String getCountOvertime() {
		return countOvertime;
	}

	public void setCountOvertime(String countOvertime) {
		this.countOvertime = countOvertime;
	}
	
	public String getCountLeave() {
		return countLeave;
	}

	public void setCountLeave(String countLeave) {
		this.countLeave = countLeave;
	}
	
	public String getCountPunch() {
		return countPunch;
	}

	public void setCountPunch(String countPunch) {
		this.countPunch = countPunch;
	}
	
	public String getCountExpense() {
		return countExpense;
	}

	public void setCountExpense(String countExpense) {
		this.countExpense = countExpense;
	}
	
	@Length(min=0, max=255, message="备用字段1长度必须介于 0 和 255 之间")
	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	
	@Length(min=0, max=255, message="备用字段2长度必须介于 0 和 255 之间")
	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	
}