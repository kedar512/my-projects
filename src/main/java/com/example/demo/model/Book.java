package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class Book {
	
	@Id
    @SequenceGenerator(name="seq", sequenceName = "BOOKS_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "TITLE", nullable = false)
    private String title;
	
	@Column(name = "PAGES", nullable = false)
    private Long pages;
	
	@Column(name = "PUBLICATION_DATE")
	private LocalDateTime publicationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPages() {
		return pages;
	}

	public void setPages(Long pages) {
		this.pages = pages;
	}

	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}
}
