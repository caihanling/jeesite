/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.ActEntity;

/**
 * 未打卡申请Entity
 * @author chl
 * @version 2016-01-22
 */
public class OaPunch extends ActEntity<OaPunch> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private User user;		// 申请用户
	private Office office;		// 归属部门
	private String post;		// 岗位
	private String reason;		// 未打卡原因
	private Date punchTime;		// 未打卡时间
	private String audit1Text;		// 审批意见1
	private String audit2Text;		// 审批意见2
	private String audit3Text;		// 审批意见3
	private String audit4Text;		// 审批意见4
	private String audit5Text;		// 审批意见5
	private String audit6Text;		// 审批意见6
	private String status;		// 流程状态
	private Date backup2;		// 备用字段2（时间区间）
	private Date backup3;		// 备用字段3（时间区间）
	
	public OaPunch() {
		super();
	}

	public OaPunch(String id){
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
	
	@Length(min=0, max=255, message="未打卡原因长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="未打卡时间不能为空")
	public Date getPunchTime() {
		return punchTime;
	}

	public void setPunchTime(Date punchTime) {
		this.punchTime = punchTime;
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
	

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBackup2() {
		return backup2;
	}

	public void setBackup2(Date backup2) {
		this.backup2 = backup2;
	}
	
	public Date getBackup3() {
		return backup3;
	}

	public void setBackup3(Date backup3) {
		this.backup3 = backup3;
	}
	
}