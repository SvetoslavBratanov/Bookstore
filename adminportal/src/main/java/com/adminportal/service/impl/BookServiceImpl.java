package com.adminportal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.Book;
import com.adminportal.repository.BookRepository;
import com.adminportal.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}
	
	public Book findOne(Long id) {
		Optional <Book> bookOptional = bookRepository.findById(id);
		if(bookOptional.isPresent()) {
			return bookOptional.get();
		}		
		throw new RuntimeException("Book with such ID doesn't exist!");
		//TODO change the error

	}
	
	public void removeOne(Long id) {
		bookRepository.deleteById(id);
	}
}
