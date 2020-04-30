package com.zfz.service.basic.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 届
 */
@Data
@TableName("basic_jie")
public class Jie {

	/**
	 * id
	 */
	@TableId(type = IdType.INPUT)
	private long id;

	/**
	 * 届名
	 */
	@TableField("name")
	private String name;

	/**
	 * 年份
	 */
	@TableField("year")
	private String year;


	/**
	 * 备注
	 */
	@TableField("note")
	private String note;

	/**
	 * 创建日期
	 */
	@TableField("create_time")
	private Date createTime = new Date();
}
