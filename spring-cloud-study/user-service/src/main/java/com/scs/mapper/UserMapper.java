package com.scs.mapper;

import com.scs.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from USER where uid = #{uid}")
    User getUserById(@Param("uid") int uid);
}
