package com.jia.pay.common.util;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * @author  ZhengXing
 * createTime: 2018/5/21
 * desc:  xml相关util
 */
@Slf4j
public class XmlUtil {

    /**
     * 对象 转 xml
     */
    @SneakyThrows
    public static <T> String beanToXmlString(T obj) {
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller ms = jc.createMarshaller();
        @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();
        ms.marshal(obj, out);
        return out.toString("UTF-8");
    }

    /**
     * xml 转 对象
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T xmlToBean(String xml,Class<T> tClass) {
        JAXBContext jc = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T)unmarshaller.unmarshal(new StringReader(xml));
    }
}
