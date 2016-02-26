/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.Detail;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;
import com.thinkgem.jeesite.modules.oa.service.OaOvertimeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 加班申请Controller
 * @author chl
 * @version 2016-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaOvertime")
public class OaOvertimeController extends BaseController {

	@Autowired
	private OaOvertimeService oaOvertimeService;
	
	@ModelAttribute
	public OaOvertime get(@RequestParam(required=false) String id) {
		OaOvertime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaOvertimeService.get(id);
		}
		if (entity == null){
			entity = new OaOvertime();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaOvertime:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaOvertime oaOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<OaOvertime> page = oaOvertimeService.findPage(new Page<OaOvertime>(request, response), oaOvertime); 
		model.addAttribute("page", page);
		return "modules/oa/oaOvertimeList";
	}
	
	@RequiresPermissions("oa:oaOvertime:view")
	@RequestMapping(value = "detail")
	public String detail (OaOvertime oaOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		Page<OaOvertime> page = oaOvertimeService.findPageByHr(new Page<OaOvertime>(request, response), oaOvertime); 
		
		if (oaOvertime.getUser() != null) {
			Detail detail = oaOvertimeService.getTotalDetail(oaOvertime);
			model.addAttribute("detail", detail);
		}
		model.addAttribute("page", page);
		return "modules/oa/oaOvertimeDetail";
	}

	@RequiresPermissions("oa:oaOvertime:view")
	@RequestMapping(value = "form")
	public String form(OaOvertime oaOvertime, Model model) {
		
		String view = "oaOvertimeForm";
		
//		添加申请时姓名和部门是默认值
		oaOvertime.setUser(UserUtils.getUser());
		oaOvertime.setOffice(UserUtils.getUser().getOffice());
		
		//查看审批清单
		if (StringUtils.isNoneBlank(oaOvertime.getId())) {
			
			//环节编号
			String taskDefKey = oaOvertime.getAct().getTaskDefKey();
			
			//查看工单
			if (oaOvertime.getAct().isFinishTask()) {
				view = "oaOvertimeView";
			}
			//审核环节1
			else if ("audit1".equals(taskDefKey)) {
				view = "oaOvertimeAudit";
			}
			//审核环节2
			else if ("audit2".equals(taskDefKey)) {
				view = "oaOvertimeAudit";
			}
			//审核环节3,修改确认加班时间
			else if ("audit3".equals(taskDefKey)) {
				view = "oaOvertimeForm";
			}
			//兑现环节
			else if ("apply_end".equals(taskDefKey)) {
				view = "oaOvertimeAudit";
			}
		}
		
		
		model.addAttribute("oaOvertime", oaOvertime);
		
		return "modules/oa/" + view;
	}

	@RequiresPermissions("oa:oaOvertime:edit")
	@RequestMapping(value = "save")
	public String save(OaOvertime oaOvertime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaOvertime)){
			return form(oaOvertime, model);
		}
		oaOvertimeService.save(oaOvertime);
		addMessage(redirectAttributes, "保存加班申请成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}

	
	/***
	 * 工单执行
	 * @param oaOvertime
	 * @param model
	 * @return
	 */
	
	@RequiresPermissions("oa:oaOvertime:edit")
	@RequestMapping(value="saveAudit")
	public String saveAudit(OaOvertime oaOvertime , Model model){
		
		if (StringUtils.isBlank(oaOvertime.getAct().getFlag()) || StringUtils.isBlank(oaOvertime.getAct().getComment())) {
			addMessage(model, "请填写审核意见。");
			return form(oaOvertime, model);
		}
		oaOvertimeService.saveAudit(oaOvertime);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	@RequiresPermissions("oa:oaOvertime:edit")
	@RequestMapping(value = "delete")
	public String delete(OaOvertime oaOvertime, RedirectAttributes redirectAttributes) {
		oaOvertimeService.delete(oaOvertime);
		addMessage(redirectAttributes, "删除加班申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaOvertime/?repage";
	}

}