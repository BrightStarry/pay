package com.jia.pay.dao.config;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc: mapper 父类，注意这个类不要让 mp 扫描到！！
 */
public interface SuperMapper<T> extends BaseMapper<T> {
}
