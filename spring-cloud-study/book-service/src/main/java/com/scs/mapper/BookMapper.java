package com.scs.mapper;

import com.scs.common.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper {
    @Select("select * from BOOK where bid=#{bid}")
    Book getBookById(@Param("bid") Integer bid);

    @Select("select count from BOOK where bid=#{bid}")
    Integer getRemain(Integer bid);

    @Update("update BOOK set count=#{count} where bid=#{bid} limit 1")
    Integer setRemain(Integer bid, Integer count);
}
