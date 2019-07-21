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
@Table(name = "FACULTY")
public class Faculty {
	
	@Id
    @SequenceGenerator(name="seq", sequenceName = "FACULTY_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
    private String lastName;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID")
	private Department department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
