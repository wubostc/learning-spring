package com.scs.controller;

import com.scs.common.entity.Book;
import com.scs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{bid}")
    public Book getBookById(@PathVariable("bid")
                            @NotNull(message = "bid不能为空") Integer bid,
                            HttpServletRequest request) {
        String header = request.getHeader("x-request-color-is");
        System.out.println("x-request-color-is = " + header);

        return bookService.getBookById(bid);
    }
}
