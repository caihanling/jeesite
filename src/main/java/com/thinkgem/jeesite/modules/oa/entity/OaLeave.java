/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import sun.tools.tree.ThisExpression;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 请假申请Entity
 * @author chl
 * @version 2016-01-19
 */
public class OaLeave extends ActEntity<OaLeave> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private User user;		// 申请用户
	private Office office;		// 归属部门
	private String post;		// 岗位
	private String reason;		// 请假事由
	private Date applyTime;		// 申请时间
	private Date beginTime;		// 请假开始时间
	private Date endTime;		// 请假结束时间
	private String audit1Text;		// 审批意见1
	private String audit2Text;		// 审批意见2
	private String audit3Text;		// 审批意见3
	private String audit4Text;		// 审批意见4
	private String audit5Text;		// 审批意见5
	private String audit6Text;		// 审批意见6
	private String type;		// 请假类型
	private String overtimeTime;		// 加班时长
	private String leaveTime;		// 请假时长
	private String totalLeaveTime;		// 当月请假总时长
	private String status;		// 流程状态
	private String backup2;		// 备用字段2
	private String backup3;		// 备用字段3
	
	public OaLeave() {
		super();
	}

	public OaLeave(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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
	
	@Length(min=0, max=255, message="岗位长度必须介于 0 和 255 之间")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@Length(min=0, max=255, message="请假事由长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假开始时间不能为空")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="请假结束时间不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=255, message="审批意见1长度必须介于 0 和 255 之间")
	public String getAudit1Text() {
		return audit1Text;
	}

	public void setAudit1Text(String audit1Text) {
		this.audit1Text = audit1Text;
	}
	
	@Length(min=0, max=255, message="审批意见2长度必须介于 0 和 255 之间")
	public String getAudit2Text() {
		return audit2Text;
	}

	public void setAudit2Text(String audit2Text) {
		this.audit2Text = audit2Text;
	}
	
	@Length(min=0, max=255, message="审批意见3长度必须介于 0 和 255 之间")
	public String getAudit3Text() {
		return audit3Text;
	}

	public void setAudit3Text(String audit3Text) {
		this.audit3Text = audit3Text;
	}
	
	@Length(min=0, max=255, message="审批意见4长度必须介于 0 和 255 之间")
	public String getAudit4Text() {
		return audit4Text;
	}

	public void setAudit4Text(String audit4Text) {
		this.audit4Text = audit4Text;
	}
	
	@Length(min=0, max=255, message="审批意见5长度必须介于 0 和 255 之间")
	public String getAudit5Text() {
		return audit5Text;
	}

	public void setAudit5Text(String audit5Text) {
		this.audit5Text = audit5Text;
	}
	
	@Length(min=0, max=255, message="审批意见6长度必须介于 0 和 255 之间")
	public String getAudit6Text() {
		return audit6Text;
	}

	public void setAudit6Text(String audit6Text) {
		this.audit6Text = audit6Text;
	}
	
	@Length(min=0, max=64, message="请假类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=10, message="加班时长长度必须介于 0 和 10 之间")
	public String getOvertimeTime() {
		return overtimeTime;
	}

	public void setOvertimeTime(String overtimeTime) {
		this.overtimeTime = overtimeTime;
	}
	
	@Length(min=0, max=10, message="请假时长长度必须介于 0 和 10 之间")
	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	@Length(min=0, max=10, message="当月请假总时长长度必须介于 0 和 10 之间")
	public String getTotalLeaveTime() {
		return totalLeaveTime;
	}

	public void setTotalLeaveTime(String totalLeaveTime) {
		this.totalLeaveTime = totalLeaveTime;
	}
	

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=0, max=255, message="备用字段2长度必须介于 0 和 255 之间")
	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	
	@Length(min=0, max=64, message="备用字段3长度必须介于 0 和 64 之间")
	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(OaLeave.class).add("id", this.id).add("taskId", this.getAct().getTaskId()).toString();
	}
	
}