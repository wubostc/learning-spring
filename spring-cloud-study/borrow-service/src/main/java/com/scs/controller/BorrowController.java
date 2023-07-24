package com.scs.controller;

import com.scs.entity.UserBorrowDetail;
import com.scs.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @RequestMapping("borrow/{uid}")
    public UserBorrowDetail getBorrows(@PathVariable("uid") Integer uid) {
        return borrowService.getUserBorrowDetailByUid(uid);
    }
}
