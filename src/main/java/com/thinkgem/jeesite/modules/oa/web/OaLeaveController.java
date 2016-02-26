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
import com.thinkgem.jeesite.modules.oa.entity.OaLeave;
import com.thinkgem.jeesite.modules.oa.entity.OaOvertime;
import com.thinkgem.jeesite.modules.oa.service.OaLeaveService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 请假申请Controller
 * @author chl
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaLeave")
public class OaLeaveController extends BaseController {

	@Autowired
	private OaLeaveService oaLeaveService;
	
	@ModelAttribute
	public OaLeave get(@RequestParam(required=false) String id) {
		OaLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaLeaveService.get(id);
		}
		if (entity == null){
			entity = new OaLeave();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaLeave:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaLeave oaLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaLeave> page = oaLeaveService.findPage(new Page<OaLeave>(request, response), oaLeave); 
		model.addAttribute("page", page);
		return "modules/oa/oaLeaveList";
	}

	
	@RequiresPermissions("oa:oaLeave:view")
	@RequestMapping(value = "detail")
	public String detail(OaLeave oaLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<OaLeave> page = oaLeaveService.findPageByHr(new Page<OaLeave>(request, response), oaLeave); 
		
		
		if (oaLeave.getUser() != null) {
			Detail detail = oaLeaveService.getTotalDetail(oaLeave);
			model.addAttribute("detail", detail);
		}
		model.addAttribute("page", page);
		
		return "modules/oa/oaLeaveDetail";
	}
	
	@RequiresPermissions("oa:oaLeave:view")
	@RequestMapping(value = "form")
	public String form(OaLeave oaLeave, Model model) {
		
		String view = "oaLeaveForm";
		
		
		//查看审批清单
		if (StringUtils.isNotBlank(oaLeave.getId())) {
			
			//环节编号
			String taskDefKey = oaLeave.getAct().getTaskDefKey();
			
			
			//查看工单
			if (oaLeave.getAct().isFinishTask()) {
				view = "oaLeaveView";
			}
			//审核环节1
			else if ("audit1".equals(taskDefKey)) {
				view = "oaLeaveAudit";
			}
			//审核环节2
			else if ("audit2".equals(taskDefKey)) {
				view = "oaLeaveAudit";
			}
//			//审核环节3,确认请假时间
//			else if ("audit3".equals(taskDefKey)) {
//				view = "oaLeaveForm";
//			}
			//兑现环节
			else if ("apply_end".equals(taskDefKey)) {
				view = "oaLeaveAudit";
			}
			
		}else {
			
//		添加申请时姓名和部门是默认值	
			oaLeave.setUser(UserUtils.getUser());
			oaLeave.setOffice(UserUtils.getUser().getOffice());
		}
		
		model.addAttribute("oaLeave", oaLeave);
		return "modules/oa/" + view;
	}

	@RequiresPermissions("oa:oaLeave:edit")
	@RequestMapping(value = "save")
	public String save(OaLeave oaLeave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaLeave)){
			return form(oaLeave, model);
		}
		oaLeaveService.save(oaLeave);
		addMessage(redirectAttributes, "保存请假申请成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	/**
	 * 保存审核意见
	 * @param oaLeave
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaLeave:edit")
	@RequestMapping(value="saveAudit")
	public String saveAudit(OaLeave oaLeave , Model model) {
		
		if (StringUtils.isBlank(oaLeave.getAct().getFlag()) || StringUtils.isBlank(oaLeave.getAct().getComment())) {
			addMessage(model, "请填写审核意见。");
			return form(oaLeave, model);
		}
		oaLeaveService.saveAudit(oaLeave);
		return "redirect:" + adminPath + "/act/task/todo/";
	} 
	
	@RequiresPermissions("oa:oaLeave:edit")
	@RequestMapping(value = "delete")
	public String delete(OaLeave oaLeave, RedirectAttributes redirectAttributes) {
		oaLeaveService.delete(oaLeave);
		addMessage(redirectAttributes, "删除请假申请成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaLeave/?repage";
	}

}