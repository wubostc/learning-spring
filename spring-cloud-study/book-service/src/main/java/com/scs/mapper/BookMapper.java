package com.scs.mapper;

import com.scs.common.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookMapper {
    @Select("select * from BOOK where bid=#{bid}")
    Book getBookById(@Param("bid") Integer bid);
}
