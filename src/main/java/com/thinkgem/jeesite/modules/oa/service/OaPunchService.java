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
import com.thinkgem.jeesite.modules.oa.entity.Detail;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;
import com.thinkgem.jeesite.modules.oa.entity.OaPunch;
import com.thinkgem.jeesite.modules.oa.dao.OaPunchDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 未打卡申请Service
 * @author chl
 * @version 2016-01-22
 */
@Service
@Transactional(readOnly = true)
public class OaPunchService extends CrudService<OaPunchDao, OaPunch> {
	
	@Autowired
	private ActTaskService actTaskService;

	public OaPunch get(String id) {
		return super.get(id);
	}
	
	public List<OaPunch> findList(OaPunch oaPunch) {
		return super.findList(oaPunch);
	}
	
	public Page<OaPunch> findPage(Page<OaPunch> page, OaPunch oaPunch) {
		oaPunch.setPage(page);
		
		//未打卡列表只显示当前用户的加班信息
		oaPunch.setUser(UserUtils.getUser());
		
		page.setList(dao.findList(oaPunch));
		return page;
	}
	
	//hr只能查看到审批通过的申请(status=1)
	public Page<OaPunch> findPageByHr(Page<OaPunch> page , OaPunch oaPunch) {
		oaPunch.setPage(page);	
		page.setList(dao.findListByHr(oaPunch));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(OaPunch oaPunch) {
		
		//申请发起
		if (StringUtils.isBlank(oaPunch.getId())) {
			oaPunch.preInsert();
			
			//将流程状态改为0,表示流程开启
			oaPunch.setStatus("0");
			
			dao.insert(oaPunch);
			
			//启动流程
			actTaskService.startProcess(ActUtils.PD_PUNCH[0], ActUtils.PD_PUNCH[1], oaPunch.getId(), oaPunch.getReason());
		}
		
		//重新编辑申请
		else {
			oaPunch.preUpdate();
			dao.update(oaPunch);
			oaPunch.getAct().setComment(("yes".equals(oaPunch.getAct().getFlag())?"[重申] ": "[确认] ") + oaPunch.getAct().getComment());
			
			//完成任务流程
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(oaPunch.getAct().getFlag()) ? "1" : "0");
			actTaskService.complete(oaPunch.getAct().getTaskId(), oaPunch.getAct().getProcInsId(), oaPunch.getAct().getComment(), oaPunch.getReason(), vars);
		}
		
	}
	
	/**
	 * 审批审核保存
	 */
	@Transactional(readOnly = false)
	public void saveAudit(OaPunch oaPunch) {
		
		//设置意见
		oaPunch.getAct().setComment(("yes".equals(oaPunch.getAct().getFlag())?"[同意] ": "[驳回] ") + oaPunch.getAct().getComment());
		
		//如果驳回，修改流程状态为:2
		if("no".equals(oaPunch.getAct().getFlag())) {
			oaPunch.setStatus("2");
			dao.updateStatus(oaPunch);
		}
		
		oaPunch.preUpdate();
		
		//环节名称
		String taskDefKey = oaPunch.getAct().getTaskDefKey();
		
		//审核环节
		if ("audit1".equals(taskDefKey)) {
			oaPunch.setAudit1Text(oaPunch.getAct().getComment());
			dao.updateAudit1Text(oaPunch);
		}else if ("audit2".equals(taskDefKey)) {
			oaPunch.setAudit2Text(oaPunch.getAct().getComment());
			dao.updateAudit2Text(oaPunch);
		}else if("apply_end".equals(taskDefKey)) {
			//流程结束，将状态改为1
			oaPunch.setStatus("1");
			dao.updateStatus(oaPunch);
		}
		//未知环节
		else {
			return;
		}
		
		//提交任务流程
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oaPunch.getAct().getFlag())? "1" : "0");
		actTaskService.complete(oaPunch.getAct().getTaskId(), oaPunch.getAct().getProcInsId(), oaPunch.getAct().getComment(), vars);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaPunch oaPunch) {
		super.delete(oaPunch);
	}
	
	@Transactional (readOnly = false)
	public Detail getTotalDetail(OaPunch oaPunch) {
		Detail detail = new Detail();
		detail.setUser(oaPunch.getUser());
		detail.setOffice(oaPunch.getUser().getOffice());
		detail.setTotal(dao.getTotalByTime(oaPunch));
		return detail;
	}
	
}