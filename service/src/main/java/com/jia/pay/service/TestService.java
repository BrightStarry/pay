package com.jia.pay.service;

import com.jia.pay.dao.entity.Test;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
public interface TestService extends SuperService<Test> {

    Test selectOne(Long id);
}
