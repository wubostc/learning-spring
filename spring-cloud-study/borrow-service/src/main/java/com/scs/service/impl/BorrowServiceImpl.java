package com.scs.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.scs.common.entity.Book;
import com.scs.common.entity.Borrow;
import com.scs.common.entity.User;
import com.scs.common.exception.MyBusinessException;
import com.scs.entity.UserBorrowDetail;
import com.scs.mapper.BorrowMapper;
import com.scs.service.BorrowService;

import com.scs.service.client.BookClient;
import com.scs.service.client.UserClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

//    @Autowired
//    LoadBalancerClient loadBalancerClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private BookClient bookClient;

    @SentinelResource(value = "details", blockHandler = "block")
    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrowList = borrowMapper.getBorrowByUid(uid);


        User user = userClient.getUserById(uid);

        List<Book> bookList = borrowList.stream()
                .map(borrow ->
                        bookClient.getBookById(borrow.getBid()))
                .collect(Collectors.toList());

        return new UserBorrowDetail(user, bookList);
    }



    // 流控替代方法，入参和返回值和之前的方法保持一致
    public UserBorrowDetail block(int uid, BlockException e) {
        return new UserBorrowDetail(null, Collections.emptyList());
    }


    // 借阅服务
    @Override
    @GlobalTransactional
    public Boolean doBorrow(Integer uid, Integer bid) {
        System.out.println("RootContext.getXID() = " + RootContext.getXID());

        //1.先判断是否可借
        if (bookClient.bookRemain(bid) < 1) {
            throw MyBusinessException.fail("图书数量不足，bid: " + bid);
        }

        if (userClient.getRemain(uid) < 1) {
            throw MyBusinessException.fail("用户借阅量不足，uid: " + uid);
        }

        //2.图书数量-1
        if (!bookClient.bookBorrow(bid)) {
            throw MyBusinessException.fail("借阅图书出错，bid: " + bid);
        }

        //3.添加借阅信息
        if (borrowMapper.getBorrow(uid, bid) != null) {
            throw MyBusinessException.fail("⚠️：此图书已被该用户借阅");
        }

        if (borrowMapper.addBorrow(uid, bid) <= 0) {
            throw MyBusinessException.fail("❌：入录借阅信息出错");
        }

        //4.用户可借-1
        if (!userClient.userBorrow(uid)) {
            throw MyBusinessException.fail("❌：用户借阅时出错");
        }

        return true;
    }
}
