package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.model.Book;
import com.example.demo.service.EmailService;
import com.example.demo.service.impl.EmailServiceImpl;
import com.example.demo.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    Controller controller;

    @RequestMapping(value = "/sendme", method = RequestMethod.GET)
    public void sendMe(@RequestParam(value = "id", required = false) Long id) throws Exception {

        Mail mail = new Mail();
        mail.setFrom("<Enter Email Here>");
        mail.setTo("<Enter Email Here>");
        mail.setSubject("Book with Category id="+id);

        Map<String, Object> model = new HashMap<String, Object>();
        List<Book> list = controller.findByBookCategory(id);
        model.put("name", controller.findByBookCategory(id).toString());
        model.put("catId", id.toString());
        model.put("location", "");
        model.put("signature", "");


        List<BookDto> table = new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            BookDto tmp = new BookDto();
            tmp.setId(list.get(i).getId());
            tmp.setName(list.get(i).getName());
            tmp.setAuthor(list.get(i).getAuthor());
            table.add(tmp);
        }
        model.put("counts", table);
        mail.setModel(model);

        emailService.sendSimpleMessage(mail);
    }
}
