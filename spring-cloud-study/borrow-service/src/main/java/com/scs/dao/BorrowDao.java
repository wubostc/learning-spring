package com.scs.dao;

import com.scs.common.entity.Borrow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowDao {
    @Select("select * from BORROW where uid=#{uid} and bid=#{bid}")
    Borrow getBorrow(@Param("uid") int uid, @Param("bid") int bid);


    @Select("select * from BORROW where bid=#{bid}")
    List<Borrow> getBorrowByBid(@Param("bid") int bid);


    @Select("select * from BORROW where uid=#{uid}")
    List<Borrow> getBorrowByUid(@Param("uid") int uid);


    @Insert("insert into BORROW(uid, bid) values(#{uid}, #{bid})")
    Integer addBorrow(Integer uid, Integer bid);
}
