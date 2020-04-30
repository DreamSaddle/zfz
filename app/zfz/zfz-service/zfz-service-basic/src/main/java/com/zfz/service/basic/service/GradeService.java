package com.zfz.service.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zfz.common.entity.PageParam;
import com.zfz.common.util.IdWorker;
import com.zfz.service.basic.dao.GradeMapper;
import com.zfz.service.basic.model.Grade;
import org.springframework.stereotype.Service;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 17:01
 */
@Service
public class GradeService extends ServiceImpl<GradeMapper, Grade> {

	public IPage<Grade> getGrades(PageParam pageParam) {
		return baseMapper.selectPage(baseMapper.toPage(pageParam), null);
	}

	public boolean addGrade(final Grade grade) {
		grade.setId(IdWorker.getId());
		return baseMapper.insert(grade) > 0;
	}

	public boolean deleteGrade(final Long id) {
		return baseMapper.deleteById(id) > 0;
	}

	public boolean updateGrade(final Grade grade) {
		return baseMapper.updateById(grade) > 0;
	}
}
