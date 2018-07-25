package com.jia.pay.dao.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class SuperEntity<T extends Model> extends Model<T> {

    protected Long id;

    protected Date createTime;

    private Date updateTime;

    @TableLogic // 逻辑删除字段标识
    protected Integer deleteFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
