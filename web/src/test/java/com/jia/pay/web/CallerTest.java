package com.jia.pay.web;

import com.jia.pay.dao.entity.Caller;
import com.jia.pay.service.CallerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  调用者相关测试
 */
@Slf4j
public class CallerTest extends PayApplicationTests {

    @Autowired
    private  CallerService callerService;

    /**
     * 生成caller
     */
    @Test
    public void testGenerateCaller() {
        Caller caller = callerService.insertCaller("测试1");
        log.info("{}",caller);
    }
}
