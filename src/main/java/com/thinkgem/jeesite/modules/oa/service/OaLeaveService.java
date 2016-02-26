/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.entity.Detail;
import com.thinkgem.jeesite.modules.oa.entity.OaLeave;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;
import com.thinkgem.jeesite.modules.oa.dao.OaLeaveDao;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 请假申请Service
 * @author chl
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class OaLeaveService extends CrudService<OaLeaveDao, OaLeave> {
	
	@Autowired
	private ActTaskService actTaskService;

	public OaLeave get(String id) {
		return super.get(id);
	}
	
	public List<OaLeave> findList(OaLeave oaLeave) {
		return super.findList(oaLeave);
	}
	
	public Page<OaLeave> findPage(Page<OaLeave> page, OaLeave oaLeave) {
		oaLeave.setPage(page);
		
		//请假列表只显示当前用户的请假信息
		oaLeave.setUser(UserUtils.getUser());
		
		page.setList(dao.findList(oaLeave));
		return page;
	}
	
	//hr只能查看到审批通过的申请(status=1)
	public Page<OaLeave> findPageByHr(Page<OaLeave> page , OaLeave oaLeave) {
		oaLeave.setPage(page);	
		page.setList(dao.findListByHr(oaLeave));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(OaLeave oaLeave) {
		
		//申请发起
		if (StringUtils.isBlank(oaLeave.getId())) {
			oaLeave.preInsert();
			
			//将流程状态改为0,表示流程开启
			oaLeave.setStatus("0");
			
			//计算请假时间
			calculateTime(oaLeave);
			
			dao.insert(oaLeave);
			
			System.err.println("oaleave1" + oaLeave);
			
			//启动流程
			actTaskService.startProcess(ActUtils.PD_LEAVE[0], ActUtils.PD_LEAVE[1], oaLeave.getId(), oaLeave.getReason());
			
			
			System.err.println("oaleave" + oaLeave);
			
			
			
			
			
			
		}
		
		//重新编辑申请
/*		else {
			
			
			oaLeave.preUpdate();
			
			//计算请假时间
			calculateTime(oaLeave);
			
			//流程结束，修改流程状态为1
			oaLeave.setStatus("1");
			
			dao.update(oaLeave);
			
			oaLeave.getAct().setComment(("yes".equals(oaLeave.getAct().getFlag())?"[重申] ": "[确认] ")+oaLeave.getAct().getComment());
			
			//完成任务流程
			Map<String , Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(oaLeave.getAct().getFlag())? "1" : "0");
			actTaskService.complete(oaLeave.getAct().getTaskId(), oaLeave.getAct().getProcInsId(), oaLeave.getAct().getComment(), oaLeave.getReason(), vars);
		}*/
		
	}
	
	/***
	 * 审核审批保存
	 */
	@Transactional(readOnly = false)
	public void saveAudit(OaLeave oaLeave) {
		System.err.println("saveAudit" + oaLeave);
		
		//设置意见
		oaLeave.getAct().setComment(("yes".equals(oaLeave.getAct().getFlag())?"[同意] ": "[驳回] ") + oaLeave.getAct().getComment());
		
		//如果驳回，修改流程状态为:2
		if ("no".equals(oaLeave.getAct().getFlag())) {
			oaLeave.setStatus("2");
			dao.updateStatus(oaLeave);
		}
		
		oaLeave.preUpdate();
		//对不同业务环节逻辑进行操作
		String taskDefKey = oaLeave.getAct().getTaskDefKey();
		System.err.println("taskDefKey:" + taskDefKey);
		
		
//		System.err.println(UserUtils.getUser().getUserType());
//		
//		//如果申请人是组长
//		if ("2".equals(UserUtils.getUser().getUserType())) {
//			oaLeave.setAudit1Text("同意");
//			dao.updateAudit1Text(oaLeave);
//			System.err.println(oaLeave.getAudit1Text());
//			Map<String, Object> vars = Maps.newHashMap();
//			vars.put("pass", "1");
//			oaLeave.getAct().setProcDefKey("apply_end");
//			actTaskService.complete(oaLeave.getAct().getTaskId(), oaLeave.getAct().getProcInsId(), oaLeave.getAct().getComment(), vars);
//			return ;
//		}
		
		
		//审核环节
		if ("audit1".equals(taskDefKey)) {
			oaLeave.setAudit1Text(oaLeave.getAct().getComment());
			dao.updateAudit1Text(oaLeave);
		}else if ("audit2".equals(taskDefKey)) {
			oaLeave.setAudit2Text(oaLeave.getAct().getComment());
			dao.updateAudit2Text(oaLeave);
		}else if("apply_end".equals(taskDefKey)) {
			//流程结束，修改流程状态为1
			oaLeave.setStatus("1");
			dao.updateStatus(oaLeave);
		}
		//未知环节
		else {
			return;
		}
		
		//提交任务流程
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(oaLeave.getAct().getFlag())? "1" : "0");
		actTaskService.complete(oaLeave.getAct().getTaskId(), oaLeave.getAct().getProcInsId(), oaLeave.getAct().getComment(), vars);
		
	
	}
	
	@Transactional(readOnly = false)
	public void delete(OaLeave oaLeave) {
		super.delete(oaLeave);
	}
	
	@Transactional (readOnly = false)
	public Detail getTotalDetail(OaLeave oaLeave) {
		Detail detail = new Detail();
		detail.setUser(oaLeave.getUser());
		detail.setOffice(oaLeave.getUser().getOffice());
		detail.setTotal(dao.getTotalByTime(oaLeave));
		return detail;
	}
	
	
	/**
	 * 计算请假时间函数
	 * @throws ParseException 
	 */
	public void calculateTime(OaLeave oaLeave) {
		
		Date begin = oaLeave.getBeginTime();
		Date end = oaLeave.getEndTime();

		
		String begin1 = DateUtils.formatDate(begin, "yyyy-MM-dd");
		String end1 = DateUtils.formatDate(end, "yyyy-MM-dd");
		
		
		String begin2 = DictUtils.getDictLabel(oaLeave.getBackup2(), "leave_time", "");
		String end2 = DictUtils.getDictLabel(oaLeave.getBackup3(), "leave_time", "");
		
		String beginTime = begin1 + " " + begin2;
		String endTime = end1 + " " + end2;

		//将拼接后的时间set到object
		try {
			oaLeave.setBeginTime(DateUtils.parseDate(beginTime, "yyyy-MM-dd HH:mm:ss"));
			oaLeave.setEndTime(DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 转化为秒
		long between = (end.getTime() - begin.getTime()) / 1000;
		double day = (double)between / (24 * 3600);
		//取day保留两位小数
		day = Double.parseDouble(String .format("%.2f",day));
		
		//通过时间间隔计算出请假天数
		double leaveDay = (Integer.parseInt(oaLeave.getBackup3()) - Integer.parseInt(oaLeave.getBackup2()))*0.5;
				
		day = day + leaveDay;
		
		oaLeave.setLeaveTime(String.valueOf(day));
	}
} 