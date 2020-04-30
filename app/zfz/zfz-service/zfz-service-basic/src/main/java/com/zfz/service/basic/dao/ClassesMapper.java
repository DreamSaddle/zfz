package com.zfz.service.basic.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfz.db.mapper.SuperMapper;
import com.zfz.service.basic.model.Classes;
import com.zfz.service.basic.vo.ClassesVO;
import org.apache.ibatis.annotations.Select;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:52
 */
public interface ClassesMapper extends SuperMapper<Classes> {

	@Select("select class.*, grade.name as gradeName from basic_class class inner join basic_grade grade on class.gradeId = grade.id")
	IPage<ClassesVO> selectPageVo(Page page);

}
