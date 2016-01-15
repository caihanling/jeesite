/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;
import com.thinkgem.jeesite.modules.oa.dao.OaOvertimeDao;

/**
 * 加班申请Service
 * @author chl
 * @version 2016-01-08
 */
@Service
@Transactional(readOnly = true)
public class OaOvertimeService extends CrudService<OaOvertimeDao, OaOvertime> {
	
	
	@Autowired
	private ActTaskService actTaskService;
	


	
	@Transactional(readOnly = false)
	public void save(OaOvertime oaOvertime) {
		
		//申请发起
		if (StringUtils.isBlank(oaOvertime.getId())) {
			oaOvertime.preInsert();
			dao.insert(oaOvertime);
			
			//启动流程
			actTaskService.startProcess(ActUtils.PD_OVERTIME[0], ActUtils.PD_OVERTIME[1], oaOvertime.getId(), oaOvertime.getReason());
			
		}
		//重新编辑申请
		else {
			oaOvertime.preUpdate();
			dao.update(oaOvertime);
			
			oaOvertime.getAct().setComment(("yes".equals(oaOvertime.getAct().getFlag())?"[重申] ":"[销毁] ")+oaOvertime.getAct().getComment());
			
			//完成流程任务
			Map<String,Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(oaOvertime.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(oaOvertime.getAct().getId(), oaOvertime.getAct().getProcInsId(), oaOvertime.getAct().getComment(), oaOvertime.getAct().getTitle(), vars);
		}
	}

	
	@Transactional(readOnly = false)
	public void delete(OaOvertime oaOvertime) {
		super.delete(oaOvertime);
	}
	
	/**
	 * 审核审批保存
	 */
	@Transactional(readOnly = false)
	public void saveAudit(OaOvertime oaOvertime) {
		
		//设置意见
		oaOvertime.getAct().setComment(("yes".equals(oaOvertime.getAct().getFlag())?"[同意] ":"[驳回] ") + oaOvertime.getAct().getComment());
		
		oaOvertime.preUpdate();
		
		//对不同环节的业务逻辑进行操作
		String taskDefKey = oaOvertime.getAct().getTaskDefKey();
		
		//审核环节
		if("leaderAudit".equals(taskDefKey)) {
			oaOvertime.setRemarks(oaOvertime.getAct().getComment());
			System.err.println(oaOvertime.getRemarks());
			dao.updateRemakrs(oaOvertime);
			
		}else if("confirme".equals(taskDefKey)) {
			oaOvertime.setRemarks(oaOvertime.getAct().getComment());
			System.err.println(oaOvertime.getRemarks());
			dao.updateRemakrs(oaOvertime);
			
		}else if("apply_end".equals(taskDefKey)) {
			
		}
		//未知环节
		else{
			return;
		}
		
		
		//提交任务流程
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oaOvertime.getAct().getFlag()) ? "1" :"0");
		actTaskService.complete(oaOvertime.getAct().getTaskId(), oaOvertime.getAct().getProcInsId(), oaOvertime.getAct().getProcInsId(), vars);
	}
	
}