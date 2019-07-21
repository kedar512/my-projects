package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PHONE_NUMBER")
public class PhoneNumber {
	
	@Id
    @SequenceGenerator(name="seq", sequenceName = "PHONE_NUMBERS_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "PHONE_NUMBER", nullable = false)
    private Long phoneNumber;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", nullable = false, referencedColumnName="ID")
	private Student student;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
