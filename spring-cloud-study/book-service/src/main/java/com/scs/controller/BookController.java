package com.scs.controller;

import com.scs.common.entity.Book;
import com.scs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{bid}")
    public Book getBookById(@PathVariable("bid") int bid) {
        return bookService.getBookById(bid);
    }
}
