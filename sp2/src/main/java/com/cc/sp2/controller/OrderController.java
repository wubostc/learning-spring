package com.cc.sp2.controller;

import com.cc.sp2.bean.OrderVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class OrderController {

    @GetMapping("/order/testid")
    public String handleOrder( @NotNull(message = "id不能为空")
                            Integer id) {

        return "ok" + id;
    }

    @PostMapping("/order/testid2")
    public String handleOrder2(@RequestBody @Valid OrderVO vo) {

        return "ok" + vo.getOrderId();
    }
}
