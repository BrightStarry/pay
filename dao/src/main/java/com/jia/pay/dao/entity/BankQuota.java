package com.jia.pay.dao.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.jia.pay.common.dto.ResultDTO;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 银行限额表
 * </p>
 *
 * @author zx
 * @since 2018-07-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class BankQuota extends SuperEntity<BankQuota> {

    public interface BankListView extends ResultDTO.BaseView{}

    /**
     * 支付方code
     */
    private String payerCode;
    /**
     * 银行表的code
     */
    @JsonView(BankListView.class)
    private String bankCode;
    /**
     * 支付类型(可包含多个，用","分隔) 0:wap支付; 1:pc快捷; 2:pc网银（没有限额）; 3:代付
     */
    private String payType;
    /**
     * 银行名(冗余字段)
     */
    @JsonView(BankListView.class)
    private String name;
    /**
     * 单笔限额,0表示未知,-1表示无限
     */
    @JsonView(BankListView.class)
    private Integer singleQuota;
    /**
     * 日限额,0表示未知,-1表示无限
     */
    @JsonView(BankListView.class)
    private Integer dayQuota;
    /**
     * 月限额,0表示未知,-1表示无限
     */
    @JsonView(BankListView.class)
    private Integer monthQuota;


}
