package com.zfz.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfz.common.entity.PageParam;

public interface SuperMapper<T> extends BaseMapper<T> {

	default Page<T> toPage(PageParam pageParam) {
		return new Page<T>(pageParam.getPage(), pageParam.getSize());
	}

}
