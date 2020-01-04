package com.zfz.service.basic.service.impl;

import com.zfz.common.util.IdWorker;
import com.zfz.service.basic.dao.GradeMapper;
import com.zfz.service.basic.domain.Grade;
import com.zfz.service.basic.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 17:01
 */
@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	GradeMapper gradeMapper;


	@Override
	public List<Grade> getGrades() {
		return gradeMapper.selectList(null);
	}

	@Override
	public boolean addGrade(final Grade grade) {
		grade.setId(IdWorker.getId());
		return gradeMapper.insert(grade) > 0;
	}

	@Override
	public boolean deleteGrade(final Long id) {
		return gradeMapper.deleteById(id) > 0;
	}

	@Override
	public boolean updateGrade(final Grade grade) {
		return gradeMapper.updateById(grade) > 0;
	}
}
