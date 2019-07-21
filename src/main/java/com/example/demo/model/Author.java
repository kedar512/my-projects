package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHOR")
public class Author {

	@Id
    @SequenceGenerator(name="seq", sequenceName = "AUTHORS_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
    private String lastName;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_AUTHOR", joinColumns = {
			@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "BOOK_ID", referencedColumnName = "ID") })
	private Set<Book> bookAuthor;

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

	public Set<Book> getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(Set<Book> bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
}
