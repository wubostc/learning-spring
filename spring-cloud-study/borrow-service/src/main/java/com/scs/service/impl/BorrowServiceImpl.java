package com.scs.service.impl;

import com.scs.common.entity.Book;
import com.scs.common.entity.Borrow;
import com.scs.common.entity.User;
import com.scs.entity.UserBorrowDetail;
import com.scs.mapper.BorrowMapper;
import com.scs.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrowList = borrowMapper.getBorrowByUid(uid);

        RestTemplate template = new RestTemplate();

        User user = template.getForObject("http://localhost:8101/user/" + uid, User.class);

        List<Book> bookList = borrowList.stream()
                .map(borrow ->
                        template.getForObject("http://localhost:8201/book/" + borrow.getBid(), Book.class))
                .collect(Collectors.toList());

        return new UserBorrowDetail(user, bookList);
    }
}
