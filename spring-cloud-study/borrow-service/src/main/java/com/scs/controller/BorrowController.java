package com.scs.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.scs.common.error.MyBizError;
import com.scs.common.exception.MyBusinessException;
import com.scs.entity.UserBorrowDetail;
import com.scs.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @RequestMapping("borrow/{uid}")
    public UserBorrowDetail getBorrows(@PathVariable("uid") Integer uid) {
        return borrowService.getUserBorrowDetailByUid(uid);
    }



    @RequestMapping("borrow2/{uid}")
    @SentinelResource(value = "getBorrows2", blockHandler = "blockHandlerForGetBorrows2")
    public UserBorrowDetail getBorrows2(@PathVariable("uid") Integer uid) throws InterruptedException {
        Thread.sleep(1000);
        return borrowService.getUserBorrowDetailByUid(uid);
    }

    // 限流时，使用此handler
    public UserBorrowDetail blockHandlerForGetBorrows2(Integer uid, BlockException e) {
        throw new MyBusinessException(MyBizError.FLOW_LIMITING, "请稍后再试！");
    }



    // spring.cloud.sentinel.block-page 配置限流操作，重定向到 /blocked
    @RequestMapping("/blocked")
    @ResponseBody
    public JSONObject blocked() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", MyBizError.FLOW_LIMITING.getCode());
        jsonObject.put("msg", "Blocked by Sentinel (flow limiting)");
        return jsonObject;
    }


    // 热点限流：指定第0个参数限流 (a)
    @RequestMapping("test")
    @SentinelResource("test")
    public String test(@RequestParam(name = "a", required = false) Integer a,
                       @RequestParam(name = "b", required = false) Integer b,
                       @RequestParam(name = "c", required = false) Integer c) {
        return "test: a = " + a + " b = " + b + " c = " + c;
    }



    //////////////////////////////////////////////////
    @RequestMapping("/borrow/take/{uid}/{bid}")
    public JSONObject borrow(@PathVariable("uid") Integer uid,
                             @PathVariable("bid") Integer bid) {
        JSONObject json = new JSONObject();

        borrowService.doBorrow(uid, bid);

        json.put("code", 0);
        json.put("result", "ok");
        return json;
    }
}
