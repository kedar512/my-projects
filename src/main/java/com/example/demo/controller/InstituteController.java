package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Author;
import com.example.demo.model.Department;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.service.InstituteService;

@RestController()
@RequestMapping(value = "/institute")
public class InstituteController {

	@Autowired
	InstituteService instituteService;

	@RequestMapping(value = "/departments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addDepartment(@RequestBody com.example.demo.dto.Department departmentRequest) {

		Department department = instituteService.addDepartment(departmentRequest);

		if (null != department) {
			return HttpStatus.OK;
		}

		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	@RequestMapping(value = "/subjects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addPhoneNumber(@RequestBody com.example.demo.dto.Subject subjectRequest) {

		Subject subject = instituteService.addSubject(subjectRequest);

		if (null != subject) {
			return HttpStatus.OK;
		}

		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/students", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addCollegeStudent(
			@RequestBody com.example.demo.dto.CollegeStudent collegeStudentRequest) {
		Student student = instituteService.addCollegeStudent(collegeStudentRequest);
		
		if (null != student) {
			return HttpStatus.OK;
		}
		
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@RequestMapping(value = "/authors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus addAuthor(@RequestBody com.example.demo.dto.Author authorRequest) {

		Author author = instituteService.addAuthor(authorRequest);

		if (null != author) {
			return HttpStatus.OK;
		}

		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
