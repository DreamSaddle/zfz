package com.zfz.service.basic.service;

import com.zfz.service.basic.domain.Grade;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 16:59
 */
public interface GradeService {

	/**
	 * 获取所有年级信息
	 */
	List<Grade> getGrades();

	/**
	 * 添加年级
	 * @param grade
	 * @return
	 */
	boolean addGrade(Grade grade);

	/**
	 * 删除年级
	 * @param id
	 * @return
	 */
	boolean deleteGrade(Long id);

	/**
	 * 修改年级
	 * @param grade
	 * @return
	 */
	boolean updateGrade(Grade grade);
}
