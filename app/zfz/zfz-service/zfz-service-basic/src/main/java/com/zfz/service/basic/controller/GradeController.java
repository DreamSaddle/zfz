package com.zfz.service.basic.controller;

import com.zfz.common.annotation.BindingResultValid;
import com.zfz.common.api.Result;
import com.zfz.service.basic.domain.Grade;
import com.zfz.service.basic.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 17:02
 */
@RestController
@RequestMapping("/api/v1/grade")
public class GradeController {

	@Autowired
	GradeService gradeService;

	@GetMapping("/list")
	public Result getGrades() {
		return Result.success(gradeService.getGrades());
	}

	@PostMapping("/add")
	@BindingResultValid
	public Result addGrade(@Valid Grade grade, BindingResult bindingResult) {
		return Result.smart(gradeService.addGrade(grade));
	}

	@DeleteMapping("/delete/{id}")
	public Result deleteGrade(@PathVariable("id") Long id) {
		return Result.smart(gradeService.deleteGrade(id));
	}

	@PutMapping("/update")
	@BindingResultValid
	public Result updateGrade(@Valid Grade grade, BindingResult bindingResult) {
		return Result.smart(gradeService.updateGrade(grade));
	}

}
