package com.jia.pay.common.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author  ZhengXing
 * createTime: 2018/7/6
 * desc:  批量查询 请求记录 结果
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class RequestListForm extends BaseForm{

    /**
     * 请求id
     */
    private List<String> requestIds;


    /**
     * 关联id
     */
    private List<String> relateIds;
}
