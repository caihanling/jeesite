/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ceshi.service.ceshi;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ceshi.entity.ceshi.MytestData;
import com.thinkgem.jeesite.modules.ceshi.dao.ceshi.MytestDataDao;

/**
 * ceshiService
 * @author chl
 * @version 2016-01-07
 */
@Service
@Transactional(readOnly = true)
public class MytestDataService extends CrudService<MytestDataDao, MytestData> {

	public MytestData get(String id) {
		return super.get(id);
	}
	
	public List<MytestData> findList(MytestData mytestData) {
		return super.findList(mytestData);
	}
	
	public Page<MytestData> findPage(Page<MytestData> page, MytestData mytestData) {
		return super.findPage(page, mytestData);
	}
	
	@Transactional(readOnly = false)
	public void save(MytestData mytestData) {
		super.save(mytestData);
	}
	
	@Transactional(readOnly = false)
	public void delete(MytestData mytestData) {
		super.delete(mytestData);
	}
	
}