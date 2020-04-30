package com.zfz.service.basic.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 14:48
 */
@TableName("basic_class")
@Data
public class Classes {

	@TableId(type = IdType.INPUT)
	private long id;

	@NotBlank(message = "班级名称不能为空")
	private String name;

	@NotNull(message = "请为班级指定年级")
	private long gradeId;

	private long jie;

	private long teacherId;
}
