package com.scs.service;

import com.scs.entity.UserBorrowDetail;

public interface BorrowService {
    UserBorrowDetail getUserBorrowDetailByUid(int uid);
}
