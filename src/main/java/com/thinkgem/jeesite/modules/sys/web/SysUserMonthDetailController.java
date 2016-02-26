/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.SysUserMonthDetail;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysUserMonthDetailService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 当月工作详情Controller
 * @author chl
 * @version 2016-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserMonthDetail")
public class SysUserMonthDetailController extends BaseController {

	@Autowired
	private SysUserMonthDetailService sysUserMonthDetailService;
	
	@ModelAttribute
	public SysUserMonthDetail get(@RequestParam(required=false) String id) {
		SysUserMonthDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserMonthDetailService.get(id);
		}
		if (entity == null){
			entity = new SysUserMonthDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysUserMonthDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserMonthDetail sysUserMonthDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		
		sysUserMonthDetailService.getMonthDetail(sysUserMonthDetail , user);
		
		Page<SysUserMonthDetail> page = sysUserMonthDetailService.findPage(new Page<SysUserMonthDetail>(request, response), sysUserMonthDetail); 
		model.addAttribute("page", page);
		return "modules/sys/sysUserMonthDetailList";
	}

	@RequiresPermissions("sys:sysUserMonthDetail:view")
	@RequestMapping(value = "form")
	public String form(SysUserMonthDetail sysUserMonthDetail, Model model) {
		model.addAttribute("sysUserMonthDetail", sysUserMonthDetail);
		return "modules/sys/sysUserMonthDetailForm";
	}

	@RequiresPermissions("sys:sysUserMonthDetail:edit")
	@RequestMapping(value = "save")
	public String save(SysUserMonthDetail sysUserMonthDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserMonthDetail)){
			return form(sysUserMonthDetail, model);
		}
		sysUserMonthDetailService.save(sysUserMonthDetail);
		addMessage(redirectAttributes, "保存当月工作详情成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserMonthDetail/?repage";
	}
	
	@RequiresPermissions("sys:sysUserMonthDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserMonthDetail sysUserMonthDetail, RedirectAttributes redirectAttributes) {
		sysUserMonthDetailService.delete(sysUserMonthDetail);
		addMessage(redirectAttributes, "删除当月工作详情成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserMonthDetail/?repage";
	}

}