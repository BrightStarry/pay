package com.jia.pay.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.pay.api.fuyou.FuYouTemplate;
import com.jia.pay.api.fuyou.api.FuYouReplacePayQueryApi;
import com.jia.pay.common.enums.PayTypeEnum;
import com.jia.pay.common.enums.PayerBankPayerTypeEnum;
import com.jia.pay.common.enums.PayerEnum;
import com.jia.pay.common.form.PayForm;
import com.jia.pay.common.form.ReplacePayForm;
import com.jia.pay.common.util.DateUtil;
import com.jia.pay.dao.entity.Request;
import com.jia.pay.service.BankService;
import com.jia.pay.service.PayerBankService;
import com.jia.pay.service.RequestService;
import com.jia.pay.service.TestService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author  ZhengXing
 * createTime: 2018/7/4
 * desc:  通用测试
 */
public class CommonTest extends PayApplicationTests {

    @Autowired
    private TestService testService;

    @Autowired
    private PayerBankService payerBankService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private BankService bankService;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private FuYouTemplate fuYouTemplate;


    @Test
    public void test1() {
//        com.jia.pay.dao.entity.Test test = new com.jia.pay.dao.entity.Test();
//        test.setUsername("a").setPwd("b");
//        testMapper.insert(test);

//        com.jia.pay.dao.entity.Test test = testMapper.selectById(1000L);
//        com.jia.pay.dao.entity.Test test1 = new com.jia.pay.dao.entity.Test();
//        test1.setId(1000L);
//        test1.setUsername("x");
//        testMapper.updateById(test1);

//        Integer integer = testMapper.deleteById(1001L);


//        List<com.jia.pay.dao.entity.Test> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add(new com.jia.pay.dao.entity.Test()
//                    .setUsername("name" + i));
//        }
//        testService.insertBatch(list);


//        Page<com.jia.pay.dao.entity.Test> page = testService.selectPage(new Page<>(1, 10));
//        System.out.println(page);
//
//        testService.selectOne(1002L);
//        testService.selectOne(1002L);
//        testService.selectOne(1002L);
//        com.jia.pay.dao.entity.Test t = new com.jia.pay.dao.entity.Test().setUsername("xxxx");
//                t.setId(1003L);
//        testService.updateById(t);



    }

    @Test
    public void testPayerBankCode() {
        String s = payerBankService.selectBankCode(PayerEnum.FUYOU, "0001", PayerBankPayerTypeEnum.DEFAULT);
        System.out.println(s);
    }

    @Test
    public void testRequest() {
        Request xx = requestService.selectOneByRequestId("1","id");
        System.out.println(xx);
    }


    @Test
    public void testGenerateCache() {
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0001", PayerBankPayerTypeEnum.DEFAULT);
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0002", PayerBankPayerTypeEnum.DEFAULT);
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0003", PayerBankPayerTypeEnum.DEFAULT);
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0004", PayerBankPayerTypeEnum.DEFAULT);
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0005", PayerBankPayerTypeEnum.DEFAULT);
        payerBankService.selectBankCode(PayerEnum.FUYOU, "0006", PayerBankPayerTypeEnum.DEFAULT);
    }


    /**
     * 生成 支付请求参数
     */
    @Test
    @SneakyThrows
    public void testPayRequestJson(){
        PayForm payForm = new PayForm();
        payForm.setPayType(PayTypeEnum.WAP_PAY.getCode());
        payForm.setMoney("0.01");
        payForm.setBankCard("6228480329466999078");
        payForm.setIdCard("331002199604160613");
        payForm.setName("郑星");
        payForm.setOrderTime(new Date());
        payForm.setPhone("17826824998");
        payForm.setUserId("1");
        payForm.setToken("MJNQSAUBTNPGOYCFIGGXIQYRDMCORGQY");
        payForm.setPayerCode(PayerEnum.FUYOU.getCode());
        payForm.setBankCode("0011");
        payForm.setOrderId("1");

        String s = objectMapper.writeValueAsString(payForm);
        System.out.println(s);
    }

    /**
     * 生成代付请求参数
     */
    @Test
    @SneakyThrows
    public void testReplacePayRequestJson(){
        ReplacePayForm payForm = new ReplacePayForm();
        payForm.setMoney("0.01");
        payForm.setBankCard("6228480329466999078");
        payForm.setName("郑星");
        payForm.setPhone("17826824998");
        payForm.setToken("MJNQSAUBTNPGOYCFIGGXIQYRDMCORGQY");
        payForm.setPayerCode(PayerEnum.LIANLIAN.getCode());
        payForm.setBankCode("0011");
        payForm.setOrderId("1");

        String s = objectMapper.writeValueAsString(payForm);
        System.out.println(s);
    }

    /**
     * 测试代付查询
     */
    @Test
    public void testReplacePay() {
        FuYouReplacePayQueryApi.WrapResponse wrapResponse = fuYouTemplate.replacePayQuery("1530869032909DKCXMRL", DateUtil.parseDate("2018-07-06 17:23:53", DateUtil.FMT_Y_M_D_H_M_S));
        System.out.println(wrapResponse);
    }
}
