package com.zfz.service.basic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfz.service.basic.domain.Classes;
import com.zfz.service.basic.vo.ClassesVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:52
 */
public interface ClassesMapper extends BaseMapper<Classes> {

	@Select("select class.*, grade.name as gradeName from basic_class class inner join basic_grade grade on class.grade = grade.id")
	IPage<ClassesVO> selectPageVo(Page page);

}
