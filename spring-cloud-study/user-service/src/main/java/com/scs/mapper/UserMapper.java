package com.scs.mapper;

import com.scs.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from USER where uid = #{uid}")
    User getUserById(@Param("uid") Integer uid);


    @Select("select book_count from USER where uid = #{uid}")
    Integer getUserBookRemain(Integer uid);

    @Update("update USER set book_count = #{count} where uid=#{uid} limit 1")
    Boolean updateBookCount(Integer uid, Integer count);
}
