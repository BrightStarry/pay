package com.jia.pay.service;

import com.jia.pay.dao.entity.Caller;
import lombok.NonNull;

/**
 * <p>
 * 调用者
 * </p>
 *
 * @author zx
 * @since 2018-07-04
 */
public interface CallerService extends SuperService<Caller>{

    Caller selectOneByToken(@NonNull String token);

    int countByName(@NonNull String name);

    Caller insertCaller(@NonNull String name);
}
