package com.scs.service.impl;

import com.scs.common.entity.Book;
import com.scs.common.entity.Borrow;
import com.scs.common.entity.User;
import com.scs.entity.UserBorrowDetail;
import com.scs.mapper.BorrowMapper;
import com.scs.service.BorrowService;
import com.scs.service.client.BookController;
import com.scs.service.client.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

//    @Autowired
//    RestTemplate template;

//    @Autowired
//    LoadBalancerClient loadBalancerClient;

    @Autowired
    private UserController userController;

    @Autowired
    private BookController bookController;

    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrowList = borrowMapper.getBorrowByUid(uid);

//        RestTemplate template = new RestTemplate();

//        ServiceInstance instance = loadBalancerClient.choose("userservice");
//        String host = instance.getHost();
//        int port = instance.getPort();


//        User user = template.getForObject("http://userservice/user/" + uid, User.class);
//
//        List<Book> bookList = borrowList.stream()
//                .map(borrow ->
//                        template.getForObject("http://bookservice/book/" + borrow.getBid(), Book.class))
//                .collect(Collectors.toList());



        User user = userController.getUserById(uid);

        List<Book> bookList = borrowList.stream()
                .map(borrow ->
                        bookController.getBookById(borrow.getBid()))
                .collect(Collectors.toList());

        return new UserBorrowDetail(user, bookList);
    }
}
