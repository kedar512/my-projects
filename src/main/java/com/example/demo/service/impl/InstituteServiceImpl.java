package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Department;
import com.example.demo.model.PhoneNumber;
import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PhoneNumberRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.service.InstituteService;

@Service
public class InstituteServiceImpl implements InstituteService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	PhoneNumberRepository phoneNumberRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Override
	public Department addDepartment(com.example.demo.dto.Department departmentRequest) {
		Department department = new Department();

		department.setDepartmentName(departmentRequest.getDepartmentName());
		department.setCreationDate(LocalDateTime.now());

		try {
			department = departmentRepository.save(department);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return department;
	}
	
	@Override
	public Subject addSubject(com.example.demo.dto.Subject subjectRequest) {
		Subject subject = new Subject();
		
		subject.setSubjectName(subjectRequest.getSubjectName());
		subject.setCreationDate(LocalDateTime.now());
		
		try {
			subject = subjectRepository.save(subject);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

	@Override
	public Student addCollegeStudent(
			com.example.demo.dto.CollegeStudent collegeStudentRequest) {

		Student student = null;

		if (null != collegeStudentRequest.getId()) {
			student = studentRepository.findById(collegeStudentRequest.getId()).orElse(null);
		}

		if (null == student) {
			student = new Student();
		}

		student.setFirstName(collegeStudentRequest.getFirstName());
		student.setLastName(collegeStudentRequest.getLastName());
		student.setCreationDate(LocalDateTime.now());
		student.setDepartment(
				departmentRepository.findByDepartmentName(collegeStudentRequest.getDepartment().getDepartmentName()));
		student.setSubjects(
				collegeStudentRequest.getSubjects().stream().map(this::convertToSubject).collect(Collectors.toSet()));

		try {
			student = studentRepository.save(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (null != student) {
			Set<PhoneNumber> phoneNumbers = new HashSet<>();
			
			for(com.example.demo.dto.PhoneNumber phoneNumberRequest : collegeStudentRequest.getPhoneNumbers()) {
				phoneNumbers.add(convertToPhoneNumber(phoneNumberRequest, student));
			}
			
			try {
				phoneNumberRepository.saveAll(phoneNumbers);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return student;
	}

	private PhoneNumber convertToPhoneNumber(com.example.demo.dto.PhoneNumber phoneNumberRequest, Student student) {
		PhoneNumber phoneNumber = new PhoneNumber();
		
		phoneNumber.setStudent(student);
		phoneNumber.setPhoneNumber(phoneNumberRequest.getPhoneNumber());
		phoneNumber.setCreationDate(LocalDateTime.now());

		return phoneNumber;
	}

	@Override
	public Author addAuthor(com.example.demo.dto.Author authorRequest) {

		Author author = new Author();

		author.setFirstName(authorRequest.getFirstName());
		author.setLastName(authorRequest.getLastName());
		author.setCreationDate(LocalDateTime.now());
		author.setBookAuthor(authorRequest.getBooks().stream().map(this::convertoToBook).collect(Collectors.toSet()));

		try {
			author = authorRepository.save(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return author;
	}

	private Book convertoToBook(com.example.demo.dto.Book bookRequest) {
		Book book = new Book();

		book.setTitle(bookRequest.getTitle());
		book.setPages(Long.valueOf(bookRequest.getPages()));
		book.setPublicationDate(LocalDateTime.now());

		return book;
	}

	private Subject convertToSubject(com.example.demo.dto.Subject subjectRequest) {
		return subjectRepository.findBySubjectName(subjectRequest.getSubjectName());
	}

}