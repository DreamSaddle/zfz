package com.zfz.db.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zfz.common.util.IdMakerUtils;
import org.apache.ibatis.reflection.MetaObject;

public class ZfzMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(final MetaObject metaObject) {
		Object id = getFieldValByName("id" , metaObject);
		if (id != null) {
			long value = (long) metaObject.getValue("id");
			if (value == 0) {
				String className = metaObject.getOriginalObject().getClass().getSimpleName();
				setFieldValByName("id", IdMakerUtils.getId(className), metaObject);
			}
		}
	}

	@Override
	public void updateFill(final MetaObject metaObject) {

	}
}
