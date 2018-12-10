package com.example.demo.model;

import javax.persistence.*;

import org.springframework.data.domain.Persistable;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Entity
public class Book implements Serializable
{
    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_details_id", referencedColumnName = "id")
    private BookDetail bookDetail;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private BookCategory bookCategory;

    public Book(){

    }

    public Book(String name){
        this.name = name;
    }

    public Book(String name, BookDetail bookDetail, BookCategory bookCategory){
        this.name = name;
        this.bookDetail = bookDetail;
        this.bookCategory = bookCategory;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {return author;}

    public void setAuthor(String author) { this.author = author; }


    public BookDetail getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }


    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, name='%s', number of pages='%d']",
                id, name, bookDetail.getNumberOfPages());
    }
}

