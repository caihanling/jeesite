/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ceshi.web.ceshi;

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
import com.thinkgem.jeesite.modules.ceshi.entity.ceshi.MytestData;
import com.thinkgem.jeesite.modules.ceshi.service.ceshi.MytestDataService;

/**
 * ceshiController
 * @author chl
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/ceshi/ceshi/mytestData")
public class MytestDataController extends BaseController {

	@Autowired
	private MytestDataService mytestDataService;
	
	@ModelAttribute
	public MytestData get(@RequestParam(required=false) String id) {
		MytestData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mytestDataService.get(id);
		}
		if (entity == null){
			entity = new MytestData();
		}
		return entity;
	}
	
	@RequiresPermissions("ceshi:ceshi:mytestData:view")
	@RequestMapping(value = {"list", ""})
	public String list(MytestData mytestData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MytestData> page = mytestDataService.findPage(new Page<MytestData>(request, response), mytestData); 
		model.addAttribute("page", page);
		return "modules/ceshi/ceshi/mytestDataList";
	}

	@RequiresPermissions("ceshi:ceshi:mytestData:view")
	@RequestMapping(value = "form")
	public String form(MytestData mytestData, Model model) {
		model.addAttribute("mytestData", mytestData);
		return "modules/ceshi/ceshi/mytestDataForm";
	}

	@RequiresPermissions("ceshi:ceshi:mytestData:edit")
	@RequestMapping(value = "save")
	public String save(MytestData mytestData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, mytestData)){
			return form(mytestData, model);
		}
		mytestDataService.save(mytestData);
		addMessage(redirectAttributes, "保存ceshi成功");
		return "redirect:"+Global.getAdminPath()+"/ceshi/ceshi/mytestData/?repage";
	}
	
	@RequiresPermissions("ceshi:ceshi:mytestData:edit")
	@RequestMapping(value = "delete")
	public String delete(MytestData mytestData, RedirectAttributes redirectAttributes) {
		mytestDataService.delete(mytestData);
		addMessage(redirectAttributes, "删除ceshi成功");
		return "redirect:"+Global.getAdminPath()+"/ceshi/ceshi/mytestData/?repage";
	}

}