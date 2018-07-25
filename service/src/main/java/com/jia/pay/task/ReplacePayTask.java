package com.jia.pay.task;

import com.jia.pay.api.strategy.replacepay.query.AbstractReplacePayQueryStrategy;
import com.jia.pay.common.enums.PayStatusEnum;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.exception.PayException;
import com.jia.pay.common.util.EnumUtil;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.service.RequestService;
import com.jia.pay.business.CallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author  ZhengXing
 * createTime: 2018/7/5
 * desc: 代付查询任务
 */
@Component
@Slf4j
public class ReplacePayTask {

    private final RequestService requestService;
    private final CallbackService callbackService;
    private final List<AbstractReplacePayQueryStrategy> replacePayQueryStrategyList;
    public ReplacePayTask(RequestService requestService, CallbackService callbackService, List<AbstractReplacePayQueryStrategy> replacePayQueryStrategyList) {
        this.requestService = requestService;
        this.callbackService = callbackService;
        this.replacePayQueryStrategyList = replacePayQueryStrategyList;
    }

    /**
     * 代付查询 定时器
     * 每8分钟执行一次
     */
    @Scheduled(cron = "0 */8 * * * ?")
    public void replacePayQueryTicker() {
        try {
            List<Request> list = requestService.selectByPayTypeAndStatus(PayTypeEnum.REPLACE_PAY, PayStatusEnum.WAIT_VERIFY, "id,request_id,payer_code,status");
            if (CollectionUtils.isEmpty(list)) {
                log.info("[代付查询定时器]当前无待验证状态代付记录");
                return;
            }
            int successNum = 0;
            int failedNum = 0;
            for (Request request : list) {
                try {
                    // 此处可以优化为，直接判断，复用PayerEnum对象
                    PayerEnum payerEnum = EnumUtil.getByCode(request.getPayerCode(), PayerEnum.class).orElseThrow(() -> new PayException("未知支付方"));
                    Boolean flag = callbackService.processReplacePay(request.getRequestId(), replacePayQueryStrategyList.get(payerEnum.getSort()), null);
                    log.info("[代付查询定时器]请求id:{},结果:{}",request.getRequestId(),flag == null ? "继续等待" : flag ? "成功" : "失败");
                    successNum++;
                } catch (PayException e) {
                    log.error("[代付查询定时器]请求id:{},异常:",request.getRequestId(),e);
                    failedNum++;
                }
            }
            log.info("[代付查询定时器]本次调用，总数:{},成功个数:{},失败个数:{}",list.size(),successNum,failedNum);
        } catch (Exception e) {
            log.error("[代付查询定时器]异常:",e);
        }
    }
}
