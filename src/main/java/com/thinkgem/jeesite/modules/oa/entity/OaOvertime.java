/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 加班申请Entity
 * 
 * @author chl
 * @version 2016-01-08
 */
public class OaOvertime extends ActEntity<OaOvertime> {

	private static final long serialVersionUID = 1L;
	private String processInstanceId; // 流程实例编号
	private Date startTime; // 加班开始时间
	private Date endTime; // 加班结束时间
	private String reason; // 加班事由

	// 运行中的流程实例
	private ProcessInstance processInstance;

	public OaOvertime() {
		super();
	}

	public OaOvertime(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "流程实例编号长度必须介于 0 和 64 之间")
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Length(min = 0, max = 255, message = "加班事由长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

}