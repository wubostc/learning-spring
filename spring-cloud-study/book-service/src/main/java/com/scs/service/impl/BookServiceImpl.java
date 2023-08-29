package com.scs.service.impl;

import com.scs.common.entity.Book;
import com.scs.mapper.BookMapper;
import com.scs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book getBookById(Integer bid) {
        return bookMapper.getBookById(bid);
    }

    @Override
    public Boolean setRemain(Integer bid, Integer count) {
        return bookMapper.setRemain(bid, count) > 0;
    }

    @Override
    public Integer getRemain(Integer bid) {
        return bookMapper.getRemain(bid);
    }


}
