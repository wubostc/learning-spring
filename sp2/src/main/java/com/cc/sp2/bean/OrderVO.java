package com.cc.sp2.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderVO {
    @NotNull(message = "订单id不能为空")
    private Integer orderId;
}
