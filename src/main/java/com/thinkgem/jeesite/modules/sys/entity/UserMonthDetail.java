package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class UserMonthDetail extends DataEntity<UserMonthDetail> {

	private static final long serialVersionUID = 1L;
	private User user; // 归属用户
	private Office office; // 归属部门
	private double count_overtime; // 加班总时长
	private double count_leave; // 请假总时长
	private double count_punch; // 未打卡总次数
	private double count_expense; // 报销总额

	public UserMonthDetail() {
		super();
	}

	public UserMonthDetail(String id) {
		super(id);
	}

	public double getCount_overtime() {
		return count_overtime;
	}

	public void setCount_overtime(double count_overtime) {
		this.count_overtime = count_overtime;
	}

	public double getCount_leave() {
		return count_leave;
	}

	public void setCount_leave(double count_leave) {
		this.count_leave = count_leave;
	}

	public double getCount_punch() {
		return count_punch;
	}

	public void setCount_punch(double count_punch) {
		this.count_punch = count_punch;
	}

	public double getCount_expense() {
		return count_expense;
	}

	public void setCount_expense(double count_expense) {
		this.count_expense = count_expense;
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

}
