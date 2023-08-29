package com.scs.service;

import com.scs.common.entity.Book;

public interface BookService {
    Book getBookById(Integer bid);

    Boolean setRemain(Integer bid, Integer count);

    Integer getRemain(Integer bid);
}
