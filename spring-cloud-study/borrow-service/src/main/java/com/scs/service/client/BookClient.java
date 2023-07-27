package com.scs.service.client;

import com.scs.common.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("bookservice")
public interface BookClient {
    @GetMapping("/book/{bid}")
    Book getBookById(@PathVariable("bid") Integer bid);
}
