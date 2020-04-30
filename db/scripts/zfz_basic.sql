

CREATE TABLE `basic_class`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) NULL COMMENT '班级名称',
  `gradeId` bigint(20) NULL COMMENT '年级id',
  `gradeName` varchar(50) NULL COMMENT '年级名称',
  `note` varchar(255) NULL COMMENT '备注',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '班级';

CREATE TABLE `basic_grade`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) NULL COMMENT '年级名',
  `jieId` bigint(20) NULL COMMENT '届id',
  `jieName` varchar(20) NULL COMMENT '届名称',
  `note` varchar(255) NULL COMMENT '备注',
  `create_time` datetime NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '年级';

CREATE TABLE `basic_jie`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(20) NULL COMMENT '届名',
  `year` varchar(20) NULL COMMENT '年份',
  `note` varchar(255) NULL COMMENT '备注',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '届';

CREATE TABLE `basic_subject`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(255) NULL COMMENT '学科名称',
  `gradeId` bigint(20) NULL COMMENT '年级id',
  `gradeName` varchar(50) NULL COMMENT '年级名称',
  `note` varchar(255) NULL COMMENT '备注',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '学科信息';

CREATE TABLE `basic_teacher`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(20) NULL COMMENT '教师姓名',
  `gender` tinyint(1) NULL COMMENT '性别(1: 男, 0:  女)',
  `idCard` varchar(20) NULL COMMENT '身份证号',
  `address` varchar(255) NULL COMMENT '家庭住址',
  `phone` varchar(30) NULL COMMENT '手机号',
  `note` varchar(255) NULL COMMENT '备注信息',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '教师';

CREATE TABLE `basic_teacher_subject`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `teacherId` bigint(20) NULL COMMENT '教师id',
  `teacherName` varchar(20) NULL COMMENT '教师姓名',
  `gradeId` bigint(20) NULL COMMENT '年级id',
  `gradeName` varchar(50) NULL COMMENT '年级名称',
  `classId` bigint(20) NULL COMMENT '班级id',
  `className` varchar(50) NULL COMMENT '班级名称',
  `subjectId` bigint(20) NULL COMMENT '科目id',
  `subjectName` varchar(255) NULL COMMENT '科目名称',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '教师授课信息';

CREATE TABLE `stu_student`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(20) NULL COMMENT '学生姓名',
  `gender` tinyint(1) NULL COMMENT '性别(1: 男,  0: 女)',
  `idCard` varchar(20) NULL COMMENT '身份证号',
  `address` varchar(255) NULL COMMENT '家庭住址',
  `phone` varchar(20) NULL COMMENT '电话号码',
  `note` varchar(255) NULL COMMENT '备注信息',
  `create_time` datetime NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT = '学生基础信息';

CREATE TABLE `stu_student_detail`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `stuId` bigint(20) NULL COMMENT '学生id',
  PRIMARY KEY (`id`)
);

