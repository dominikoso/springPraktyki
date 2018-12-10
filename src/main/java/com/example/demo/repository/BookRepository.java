package com.example.demo.repository;

import com.example.demo.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Book;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long>{
    public Book findByName(String name);
    public Book findByNameStartingWith(String name);
    public Book findByNameEndingWith(String name);
    public List<Book> findByBookCategory(BookCategory bookCategory);
}
