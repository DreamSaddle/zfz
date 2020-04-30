package com.zfz.service.basic.web.manager;

import com.zfz.common.annotation.BindingResultValid;
import com.zfz.common.api.Result;
import com.zfz.common.entity.PageParam;
import com.zfz.service.basic.model.Grade;
import com.zfz.service.basic.service.GradeService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * author: DreamSaddle
 * date: 2019年12月29日
 * time: 17:02
 */
@RestController
@RequestMapping("/api/service/basic/grade")
public class GradeController {

	@Resource
	GradeService gradeService;

	@GetMapping("/listPage")
	public Result getGrades(PageParam pageParam) {
		return Result.success(gradeService.getGrades(pageParam));
	}

	@PostMapping("/add")
	@BindingResultValid
	public Result addGrade(@Valid @RequestBody Grade grade, BindingResult bindingResult) {
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
