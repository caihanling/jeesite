/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaExpense;
import com.thinkgem.jeesite.modules.oa.dao.OaExpenseDao;

/**
 * 报销申请Service
 * @author chl
 * @version 2016-01-23
 */
@Service
@Transactional(readOnly = true)
public class OaExpenseService extends CrudService<OaExpenseDao, OaExpense> {
	
	@Autowired
	private ActTaskService actTaskService;

	public OaExpense get(String id) {
		return super.get(id);
	}
	
	public List<OaExpense> findList(OaExpense oaExpense) {
		return super.findList(oaExpense);
	}
	
	public Page<OaExpense> findPage(Page<OaExpense> page, OaExpense oaExpense) {
		oaExpense.setPage(page);
		page.setList(dao.findList(oaExpense));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(OaExpense oaExpense) {
		
		//发起申请
		if (StringUtils.isBlank(oaExpense.getId())) {
			oaExpense.preInsert();
			dao.insert(oaExpense);
			
			//启动流程
			actTaskService.startProcess(ActUtils.PD_EXPENSE[0], ActUtils.PD_EXPENSE[1], oaExpense.getId(), oaExpense.getReason());
		}
		
		//重新编辑申请
		else {
			oaExpense.preUpdate();
			dao.update(oaExpense);
			oaExpense.getAct().setComment(("yes".equals(oaExpense.getAct().getFlag())?"[重申] ": "[确认] ") + oaExpense.getAct().getComment());
			
			//完成任务流程
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(oaExpense.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(oaExpense.getAct().getTaskId(), oaExpense.getAct().getProcInsId(), oaExpense.getAct().getComment(), oaExpense.getReason(), vars);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void saveAudit(OaExpense oaExpense) {
		
		//设置意见
		oaExpense.getAct().setComment(("yes".equals(oaExpense.getAct().getFlag())?"[同意] ": "[驳回] ") + oaExpense.getAct().getComment());
		oaExpense.preUpdate();
		
		//流程名称
		String taskDefKey = oaExpense.getAct().getTaskDefKey();
		
		//审核环节
		if ("audit1".equals(taskDefKey)) {
			oaExpense.setAudit1Text(oaExpense.getAct().getComment());
			dao.updateAudit1Text(oaExpense);
		}else if ("audit2".equals(taskDefKey)) {
			oaExpense.setAudit2Text(oaExpense.getAct().getComment());
			dao.updateAudit2Text(oaExpense);
		}
		else if ("audit3".equals(taskDefKey)) {
			oaExpense.setAudit3Text(oaExpense.getAct().getComment());
			dao.updateAudit3Text(oaExpense);
		}else if ("apply_end".equals(taskDefKey)) {
			
		}
		//未知环节
		else {
			return ;
		}
		
		//提交任务流程
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oaExpense.getAct().getFlag())? "1" : "0");
		actTaskService.complete(oaExpense.getAct().getTaskId(), oaExpense.getAct().getProcInsId(), oaExpense.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaExpense oaExpense) {
		super.delete(oaExpense);
	}
	
}