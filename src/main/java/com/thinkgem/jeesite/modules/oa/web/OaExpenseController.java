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
import com.thinkgem.jeesite.modules.oa.entity.OaExpense;
import com.thinkgem.jeesite.modules.oa.service.OaExpenseService;

/**
 * 报销申请Controller
 * @author chl
 * @version 2016-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaExpense")
public class OaExpenseController extends BaseController {

	@Autowired
	private OaExpenseService oaExpenseService;
	
	@ModelAttribute
	public OaExpense get(@RequestParam(required=false) String id) {
		OaExpense entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaExpenseService.get(id);
		}
		if (entity == null){
			entity = new OaExpense();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaExpense:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaExpense oaExpense, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaExpense> page = oaExpenseService.findPage(new Page<OaExpense>(request, response), oaExpense); 
		model.addAttribute("page", page);
		return "modules/oa/oaExpenseList";
	}

	@RequiresPermissions("oa:oaExpense:view")
	@RequestMapping(value = "form")
	public String form(OaExpense oaExpense, Model model) {
		
		String view = "oaExpenseForm";
		
		if (StringUtils.isNotBlank(oaExpense.getId())) {
			
			//编号环节
			String taskDefKey = oaExpense.getAct().getTaskDefKey();
			
			//查看工单
			if (oaExpense.getAct().isFinishTask()) {
				view = "oaExpenseView";
			}
			//审核环节1(组长审批)
			else if ("audit1".equals(taskDefKey)) {
				view = "oaExpenseAudit";
			}
			//审核环节2（经理审批）
			else if ("audit2".equals(taskDefKey)) {
				view = "oaExpenseAudit";
			}
			//审核环节3（行政审批）
			else if ("audit3".equals(taskDefKey)) {
				view = "oaExpenseAudit";
			}
			//兑现环节
			else if ("apply_end".equals(taskDefKey)) {
				view = "oaExpenseAudit";
			}
		}
		
		System.err.println("(oa_expense)proc_ins_id:" + oaExpense.getAct().getProcInsId());
		
		model.addAttribute("oaExpense", oaExpense);
		return "modules/oa/" + view;
	}

	@RequiresPermissions("oa:oaExpense:edit")
	@RequestMapping(value = "save")
	public String save(OaExpense oaExpense, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaExpense)){
			return form(oaExpense, model);
		}
		oaExpenseService.save(oaExpense);
		addMessage(redirectAttributes, "保存报销申请成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	@RequiresPermissions("oa:oaExpense:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(OaExpense oaExpense , Model model) {
		
		if (StringUtils.isBlank(oaExpense.getAct().getFlag()) || StringUtils.isBlank(oaExpense.getAct().getComment())) {
			addMessage(model, "请填写审核意见。");
			return form(oaExpense, model);
		}
		oaExpenseService.saveAudit(oaExpense);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("oa:oaExpense:edit")
	@RequestMapping(value = "delete")
	public String delete(OaExpense oaExpense, RedirectAttributes redirectAttributes) {
		oaExpenseService.delete(oaExpense);
		addMessage(redirectAttributes, "删除报销申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaExpense/?repage";
	}

}