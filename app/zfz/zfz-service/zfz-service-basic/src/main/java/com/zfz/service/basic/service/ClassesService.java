package com.zfz.service.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfz.common.entity.PageInfo;
import com.zfz.service.basic.domain.Classes;
import com.zfz.service.basic.vo.ClassesVO;

import java.util.List;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:51
 */
public interface ClassesService {

	IPage<ClassesVO> getAll(PageInfo pageInfo);

	boolean addClasses(Classes classes);

	boolean updateClasses(Classes classes);

	boolean deleteClasses(Long id);
}
