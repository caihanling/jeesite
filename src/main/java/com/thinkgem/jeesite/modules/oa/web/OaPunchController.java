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
import com.thinkgem.jeesite.modules.oa.entity.OaPunch;
import com.thinkgem.jeesite.modules.oa.service.OaPunchService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 未打卡申请Controller
 * @author chl
 * @version 2016-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaPunch")
public class OaPunchController extends BaseController {

	@Autowired
	private OaPunchService oaPunchService;
	
	@ModelAttribute
	public OaPunch get(@RequestParam(required=false) String id) {
		OaPunch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaPunchService.get(id);
		}
		if (entity == null){
			entity = new OaPunch();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaPunch:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaPunch oaPunch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaPunch> page = oaPunchService.findPage(new Page<OaPunch>(request, response), oaPunch); 
		model.addAttribute("page", page);
		return "modules/oa/oaPunchList";
	}
	
	
	@RequiresPermissions("oa:oaPunch:view")
	@RequestMapping(value = "detail")
	public String detail (OaPunch oaPunch, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		Page<OaPunch> page = oaPunchService.findPageByHr(new Page<OaPunch>(request, response), oaPunch); 
		
		if (oaPunch.getUser() != null) {
			Detail detail = oaPunchService.getTotalDetail(oaPunch);
			model.addAttribute("detail", detail);
		}
		model.addAttribute("page", page);
		return "modules/oa/oaPunchDetail";
	}

	@RequiresPermissions("oa:oaPunch:view")
	@RequestMapping(value = "form")
	public String form(OaPunch oaPunch, Model model) {
		
		String view = "oaPunchForm";
		
//		添加申请时姓名和部门是默认值
		oaPunch.setUser(UserUtils.getUser());
		oaPunch.setOffice(UserUtils.getUser().getOffice());
		
		//查看审批清单
		if (StringUtils.isNotBlank(oaPunch.getId())) {
			
			//环节编号
			String taskDefKey = oaPunch.getAct().getTaskDefKey();
			
			//查看工单
			if (oaPunch.getAct().isFinishTask()) {
				view = "oaPunchView";
			}
			//审核环节1
			else if ("audit1".equals(taskDefKey)) {
				view = "oaPunchAudit";
			}
			//审核环节2
			else if ("audit2".equals(taskDefKey)) {
				view = "oaPunchAudit";
			}
			//兑现环节
			else if ("apply_end".equals(taskDefKey)) {
				view = "oaPunchAudit";
			}
			
		}
		
		model.addAttribute("oaPunch", oaPunch);
		return "modules/oa/" + view;
	}

	@RequiresPermissions("oa:oaPunch:edit")
	@RequestMapping(value = "save")
	public String save(OaPunch oaPunch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaPunch)){
			return form(oaPunch, model);
		}
		oaPunchService.save(oaPunch);
		addMessage(redirectAttributes, "保存未打卡申请成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("oa:oaPunch:edit")
	@RequestMapping(value = "delete")
	public String delete(OaPunch oaPunch, RedirectAttributes redirectAttributes) {
		oaPunchService.delete(oaPunch);
		addMessage(redirectAttributes, "删除未打卡申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaPunch/?repage";
	}

	@RequiresPermissions("oa:oaPunch:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(OaPunch oaPunch , Model model) {
		
		if (StringUtils.isBlank(oaPunch.getAct().getFlag()) || StringUtils.isBlank(oaPunch.getAct().getComment())) {
			addMessage(model, "请填写审核意见。");
			return form(oaPunch, model);
		}
		
		oaPunchService.saveAudit(oaPunch);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
}