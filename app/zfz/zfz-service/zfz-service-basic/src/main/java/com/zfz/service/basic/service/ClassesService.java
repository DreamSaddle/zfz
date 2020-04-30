package com.zfz.service.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zfz.common.entity.PageParam;
import com.zfz.common.util.IdWorker;
import com.zfz.service.basic.dao.ClassesMapper;
import com.zfz.service.basic.model.Classes;
import com.zfz.service.basic.vo.ClassesVO;
import org.springframework.stereotype.Service;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:51
 */
@Service
public class ClassesService extends ServiceImpl<ClassesMapper, Classes> {


	public IPage<ClassesVO> getAll(PageParam pageParam) {
		return baseMapper.selectPageVo(baseMapper.toPage(pageParam));
	}

	public boolean addClasses(final Classes classes) {
		classes.setId(IdWorker.getId());
		return baseMapper.insert(classes) > 0;
	}

	public boolean updateClasses(final Classes classes) {
		return baseMapper.updateById(classes) > 0;
	}

	public boolean deleteClasses(final Long id) {
		return baseMapper.deleteById(id) > 0;
	}
}
