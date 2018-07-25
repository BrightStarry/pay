package com.jia.pay.common.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author  ZhengXing
 * createTime: 2018/5/17
 * desc: bean相关
 */
public class BeanUtil {

    /**
     * dto 转 {@link Map <String,String>}
     * 该方法对于class的静态内部类无法解析
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, String> beanToMap(T obj) throws Exception {
        Map<String, String> map = BeanUtils.describe(obj);
        //会产生key为class的元素
        map.remove("class");
        map.entrySet().removeIf(item -> item.getValue() == null);

        return map;
    }

    /**
     * dto 转 {@link LinkedHashMap}
     * 获取有序的map
     * 根据类属性 默认排序
     *
     *  暂不支持父类
     */
    public static <T> LinkedHashMap<String, String> beanToOrderlyMap(T obj) throws Exception {
        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            result.put(field.getName(), (String) field.get(obj));
        }
        return result;
    }

    /**
     * dto 转 {@link LinkedHashMap}
     * 获取有序的map
     * 根据字母大小写排序
     */
    public static <T> LinkedHashMap<String, String> beanToOrderlyMapOfLetter(T obj) throws Exception {
        Map<String, String> oldMap = beanToMap(obj);
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        oldMap.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }


}
