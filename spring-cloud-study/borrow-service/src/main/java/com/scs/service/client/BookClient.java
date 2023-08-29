package com.scs.service.client;

import com.scs.common.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("book-service")
public interface BookClient {
    @GetMapping("/book/{bid}")
    Book getBookById(@PathVariable("bid") Integer bid);

    @GetMapping("/book/remain/{bid}")
    Integer bookRemain(@PathVariable("bid") Integer bid);


    @GetMapping("/book/borrow/{bid}")
    Boolean bookBorrow(@PathVariable("bid") Integer bid);
}
