package com.zfz.service.basic.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 16:54
 */
@TableName("basic_grade")
@Getter
@Setter
public class Grade {

	@TableId(type = IdType.INPUT)
	private long id;

	@NotBlank(message = "年级名称不能为空")
	private String name;

}
