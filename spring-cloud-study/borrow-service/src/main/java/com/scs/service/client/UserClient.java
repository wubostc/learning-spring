package com.scs.service.client;

import com.scs.common.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;


@FeignClient(value = "userservice", fallback = UserClientFallback.class)
@Validated
public interface UserClient {
    @GetMapping("/user/{uid}")
    User getUserById(@PathVariable("uid")
                     @NotNull(message = "id 不能为空！！！")
                     Integer id);
}
