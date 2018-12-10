package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.beans.FeatureDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.OneToOne;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    RestTemplate restTemplate;

    private static boolean isOneToOneRelation(Field property) {
        Annotation[] annotations = property.getDeclaredAnnotations();
        boolean isOneToOne = false;

        for(Annotation annotation : annotations){

            if(annotation instanceof OneToOne){
                isOneToOne = true;
            }
        }

        return isOneToOne;

    }

    public static Object copyNonNullProperties(Object target, Object in) {
        if (in == null || target == null || target.getClass() != in.getClass()) return null;

        final BeanWrapper src = new BeanWrapperImpl(in);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            if (isOneToOneRelation(property) || (providedObject != null && !(providedObject instanceof Collection<?>))) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
        return target;
    }

    public void update(Book book){
        Book forUpdate = bookRepository.findOne(book.getId());
        copyNonNullProperties(forUpdate, book);
        bookRepository.save(forUpdate);
    }
    public Book sendBook(Long id){
        Book response = restTemplate.getForObject("http://localhost:8080/test/random/send?id="+id.toString() ,Book.class);
        return response;
    }

}
