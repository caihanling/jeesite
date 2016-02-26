/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.Date;
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
import com.thinkgem.jeesite.modules.oa.dao.OaOvertimeDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 加班申请Service
 * 
 * @author chl
 * @version 2016-01-16
 */
@Service
@Transactional(readOnly = true)
public class OaOvertimeService extends CrudService<OaOvertimeDao, OaOvertime> {

	@Autowired
	private ActTaskService actTaskService;

	public OaOvertime get(String id) {
		return super.get(id);
	}

	public List<OaOvertime> findList(OaOvertime oaOvertime) {
		return super.findList(oaOvertime);
	}

	public Page<OaOvertime> findPage(Page<OaOvertime> page , OaOvertime oaOvertime) {
		oaOvertime.setPage(page);
		
		//加班列表只显示当前用户的加班信息
		oaOvertime.setUser(UserUtils.getUser());
		
		page.setList(dao.findList(oaOvertime));
		return page;
	}
	
	
	//hr只能查看到审批通过的申请(status=1)
	public Page<OaOvertime> findPageByHr(Page<OaOvertime> page , OaOvertime oaOvertime) {
		oaOvertime.setPage(page);	
		page.setList(dao.findListByHr(oaOvertime));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(OaOvertime oaOvertime) {

		// 申请发起
		if (StringUtils.isBlank(oaOvertime.getId())) {
			oaOvertime.preInsert();
			
			//将流程状态改为0,表示流程开启
			oaOvertime.setStatus("0");
			
			//计算加班时长
			calculateTime(oaOvertime);
			
			dao.insert(oaOvertime);

			// 启动流程
			actTaskService.startProcess(ActUtils.PD_OVERTIME[0],ActUtils.PD_OVERTIME[1], oaOvertime.getId(),oaOvertime.getReason());
		}

		// 重新编辑申请
		else {
			oaOvertime.preUpdate();

			//计算加班时长
			calculateTime(oaOvertime);
			
			//流程结束，修改流程状态为1
			oaOvertime.setStatus("1");
			
			dao.update(oaOvertime);

			oaOvertime.getAct().setComment(("yes".equals(oaOvertime.getAct().getFlag()) ? "[重申] ": "[完成] ") + oaOvertime.getAct().getComment());

			// 完成任务流程
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(oaOvertime.getAct().getFlag()) ? "1": "0");
			actTaskService.complete(oaOvertime.getAct().getTaskId(), oaOvertime.getAct().getProcInsId(), oaOvertime.getAct().getComment(),oaOvertime.getReason(), vars);
		}
	}

	/**
	 * 审核审批保存
	 * 
	 * @param oaOvertime
	 */
	@Transactional(readOnly = false)
	public void saveAudit(OaOvertime oaOvertime) {

		// 设置意见
		oaOvertime.getAct().setComment(("yes".equals(oaOvertime.getAct().getFlag()) ? "[同意] ": "[驳回] ") + oaOvertime.getAct().getComment());
		
		//如果驳回，修改流程状态为:2
		if("no".equals(oaOvertime.getAct().getFlag())) {
			oaOvertime.setStatus("2");
			dao.updateStatus(oaOvertime);
		}

		oaOvertime.preUpdate();

		// 对不同环节业务逻辑进行操作
		String taskDefKey = oaOvertime.getAct().getTaskDefKey();
		

		// 审核环节
		if ("audit1".equals(taskDefKey)) {
			oaOvertime.setAudit1Text(oaOvertime.getAct().getComment());
			dao.updateAudit1Text(oaOvertime);
		} else if ("audit2".equals(taskDefKey)) {
			oaOvertime.setAudit2Text(oaOvertime.getAct().getComment());
			dao.updateAudit2Text(oaOvertime);
		}
		// 加班确认
		else if ("audit3".equals(taskDefKey)) {
			dao.confirmOvertime(oaOvertime);
		} else if ("apply_end".equals(taskDefKey)) {
			
		}
		// 未知环节
		else {
			return;
		}

		// 提交任务流程
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oaOvertime.getAct().getFlag()) ? "1": "0");
		
		actTaskService.complete(oaOvertime.getAct().getTaskId(), oaOvertime.getAct().getProcInsId(), oaOvertime.getAct().getComment(),vars);
	}

	@Transactional(readOnly = false)
	public void delete(OaOvertime oaOvertime) {
		super.delete(oaOvertime);
	}
	
	
	@Transactional (readOnly = false)
	public Detail getTotalDetail(OaOvertime oaOvertime) {
		Detail detail = new Detail();
		detail.setUser(oaOvertime.getUser());
		detail.setOffice(oaOvertime.getUser().getOffice());
		detail.setTotal(dao.getTotalByTime(oaOvertime));
		return detail;
	}
	
	public void calculateTime(OaOvertime oaOvertime) {
		
		Date begin = oaOvertime.getBeginTime();
		Date end = oaOvertime.getEndTime();
		// 转化为秒,8h算一天
		long between = (end.getTime() - begin.getTime()) / 1000;
		double day = (double)between / (8 * 3600);
		oaOvertime.setCount(String .format("%.2f",day));
		
	}





}