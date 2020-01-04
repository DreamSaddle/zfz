package com.zfz.service.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfz.common.entity.PageInfo;
import com.zfz.common.util.IdWorker;
import com.zfz.service.basic.dao.ClassesMapper;
import com.zfz.service.basic.domain.Classes;
import com.zfz.service.basic.service.ClassesService;
import com.zfz.service.basic.vo.ClassesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:51
 */
@Service
public class ClassesServiceImpl implements ClassesService {

	@Autowired
	private ClassesMapper classesMapper;

	@Override
	public IPage<ClassesVO> getAll(PageInfo pageInfo) {
		Page page = new Page();
		page.setCurrent(pageInfo.getPage()).setSize(pageInfo.getSize());
		return classesMapper.selectPageVo(page);
	}

	@Override
	public boolean addClasses(final Classes classes) {
		classes.setId(IdWorker.getId());
		return classesMapper.insert(classes) > 0;
	}

	@Override
	public boolean updateClasses(final Classes classes) {
		return classesMapper.updateById(classes) > 0;
	}

	@Override
	public boolean deleteClasses(final Long id) {
		return classesMapper.deleteById(id) > 0;
	}
}
