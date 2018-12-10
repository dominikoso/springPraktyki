package com.example.demo.service;

import com.example.demo.model.Book;

public interface BookService {
    public void update(Book book);
    public Book sendBook(Long id);

}
