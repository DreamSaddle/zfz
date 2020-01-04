package com.zfz.service.basic.controller;

import com.zfz.common.annotation.BindingResultValid;
import com.zfz.common.api.Result;
import com.zfz.common.entity.PageInfo;
import com.zfz.service.basic.domain.Classes;
import com.zfz.service.basic.service.ClassesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 15:04
 */
@Slf4j
@RestController
@RequestMapping("/classes")
public class ClassesController {

	@Autowired
	private ClassesService classesService;

	@GetMapping("/list")
	public Result getAllClasses(PageInfo pageInfo) {
		return Result.success(classesService.getAll(pageInfo));
	}

	@PostMapping("/add")
	@BindingResultValid
	public Result addClasses(@Valid Classes classes, BindingResult bindingResult) {
		log.info("添加班级信息...");
		return Result.smart(classesService.addClasses(classes));
	}

	@PutMapping("/update")
	@BindingResultValid
	public Result updateClasses(@Valid Classes classes, BindingResult bindingResult) {
		return Result.smart(classesService.updateClasses(classes));
	}

	@DeleteMapping("/delete/{id}")
	public Result deleteClasses(@PathVariable("id") Long id) {
		return Result.smart(classesService.deleteClasses(id));
	}
}
