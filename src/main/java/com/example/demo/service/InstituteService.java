package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.Department;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;

public interface InstituteService {
	
	Department addDepartment(com.example.demo.dto.Department departmentRequest);
	
	Subject addSubject(com.example.demo.dto.Subject subjectRequest);
	
	Student addCollegeStudent(com.example.demo.dto.CollegeStudent collegeStudentRequest);
	
	Author addAuthor(com.example.demo.dto.Author authorRequest);
}
